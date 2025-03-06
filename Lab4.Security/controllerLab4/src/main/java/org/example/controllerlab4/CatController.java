//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.controllerlab4;

import java.util.List;

import org.example.repositorylab4.dto.CatCreateDto;
import org.example.repositorylab4.dto.CatDto;
import org.example.repositorylab4.model.User;
import org.example.servicelab4.impl.CatService;
import org.example.servicelab4.impl.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping({"/cats"})
public class CatController {
    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping({"/{id}"})
    public CatDto getCatById(@PathVariable Long id) {
        return this.catService.getCatById(id);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping
    public List<CatDto> getAllCats() {
        return this.catService.getAllCats();
    }

    @PreAuthorize("hasRole('OWNER')")
    @GetMapping({"/my-cats"})
    public List<CatDto> getAllCatsByOwnerId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MyUserDetails userDetails = (MyUserDetails)authentication.getPrincipal();
        User user = userDetails.getUser();
        if (user.getOwner() != null) {
            return this.catService.getAllCatsByOwnerId(user.getOwner().getId());
        } else {
            throw new RuntimeException("User is not an owner");
        }
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatCreateDto catDto) {
        CatDto createdCat = this.catService.createCat(catDto);
        return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @PutMapping({"/{catId}/friends/{friendId}"})
    public void addFriendToCat(@PathVariable Long catId, @PathVariable Long friendId) {
        this.catService.addFriendToCat(catId, friendId);
    }

    @PreAuthorize("hasAnyRole('OWNER', 'ADMIN')")
    @DeleteMapping({"/{catId}/friends/{friendId}"})
    public void removeFriendFromCat(@PathVariable Long catId, @PathVariable Long friendId) {
        this.catService.removeFriendFromCat(catId, friendId);
    }
}
