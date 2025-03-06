package org.example.externalinterfaceservice.controllers;

import java.util.List;

import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.example.common.model.User;
import org.example.externalinterfaceservice.messaging.CatServiceMessaging;
import org.example.externalinterfaceservice.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8081/cats";
    private final CatServiceMessaging catServiceClient;


    @Autowired
    public CatController(RestTemplate restTemplate, CatServiceMessaging catServiceClient) {
        this.restTemplate = restTemplate;
        this.catServiceClient = catServiceClient;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, CatDto.class);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/all-cats")
    public List<CatDto> getAllCats() {
        return restTemplate.exchange(
                baseUrl + "/all-cats",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CatDto>>() {}
        ).getBody();
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping("/my-cats")
    public List<CatDto> getAllCatsByOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOwner() != null) {
            return restTemplate.exchange(
                    baseUrl + "/owner/" + user.getOwner().getId(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<CatDto>>() {}
            ).getBody();
        } else {
            throw new RuntimeException("User is not an owner");
        }
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatCreateDto catDto) {
        CatDto createdCat = restTemplate.postForObject(baseUrl, catDto, CatDto.class);
        return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PutMapping({"/{catId}/friends/{friendId}"})
    public void addFriendToCat(@PathVariable Long catId, @PathVariable Long friendId) {
        this.catServiceClient.addFriendToCat(catId, friendId);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping({"/{catId}/friends/{friendId}"})
    public void removeFriendFromCat(@PathVariable Long catId, @PathVariable Long friendId) {
        this.catServiceClient.removeFriendFromCat(catId, friendId);
    }
}
