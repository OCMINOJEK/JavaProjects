//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.controllerlab4;

import java.util.List;

import org.example.repositorylab4.dto.OwnerCreateDto;
import org.example.repositorylab4.dto.OwnerDto;
import org.example.repositorylab4.model.User;
import org.example.servicelab4.impl.MyUserDetails;
import org.example.servicelab4.impl.OwnerService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping({"/owners"})
public class OwnerController {
    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerCreateDto ownerDto) {
        OwnerDto createdOwner = this.ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @GetMapping({"/{id}"})
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OwnerDto getOwnerById(@PathVariable Long id) {
        return this.ownerService.getOwnerById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OwnerDto> getAllOwners() {
        return this.ownerService.getAllOwners();
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
