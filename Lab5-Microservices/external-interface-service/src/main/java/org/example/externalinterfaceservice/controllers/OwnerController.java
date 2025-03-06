package org.example.externalinterfaceservice.controllers;

import java.util.List;

import org.example.common.dto.OwnerCreateDto;
import org.example.common.dto.OwnerDto;
import org.example.common.model.User;
import org.example.externalinterfaceservice.messaging.OwnerServiceMessaging;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final RestTemplate restTemplate;
    private final String baseUrl = "http://localhost:8082/owners";
    private final OwnerServiceMessaging ownerService;

    @Autowired
    public OwnerController(RestTemplate restTemplate, OwnerServiceMessaging ownerService) {
        this.restTemplate = restTemplate;
        this.ownerService = ownerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerCreateDto ownerDto) {
        OwnerDto createdOwner = restTemplate.postForObject(baseUrl, ownerDto, OwnerDto.class);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OwnerDto getOwnerById(@PathVariable Long id) {
        return restTemplate.getForObject(baseUrl + "/" + id, OwnerDto.class);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OwnerDto> getAllOwners() {
        return restTemplate.exchange(
                baseUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<OwnerDto>>() {}
        ).getBody();
    }

    @PostMapping({"/{ownerId}/cats/{catId}"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void addCatToOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        this.ownerService.addCatToOwner(ownerId, catId);
    }

    @PostMapping({"/add-cat"})
    @PreAuthorize("hasAnyRole('OWNER')")
    public void addCatToOwner(@PathVariable Long catId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOwner() != null) {
            this.ownerService.addCatToOwner(user.getOwner().getId(), catId);
        } else {
            throw new RuntimeException("User is not an owner");
        }
    }

    @DeleteMapping({"/{ownerId}/cats/{catId}"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeCatFromOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        this.ownerService.removeCatFromOwner(ownerId, catId);
    }

    @PostMapping({"/remove-cat"})
    @PreAuthorize("hasAnyRole('OWNER')")
    public void removeCatFromOwner(@PathVariable Long catId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOwner() != null) {
            this.ownerService.removeCatFromOwner(user.getOwner().getId(), catId);
        } else {
            throw new RuntimeException("User is not an owner");
        }
    }
}
