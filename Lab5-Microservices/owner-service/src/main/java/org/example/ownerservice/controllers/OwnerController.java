package org.example.ownerservice.controllers;

import java.util.List;

import org.example.common.dto.OwnerCreateDto;
import org.example.common.dto.OwnerDto;
import org.example.ownerservice.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/owners")
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<OwnerDto> createOwner(@RequestBody OwnerCreateDto ownerDto) {
        OwnerDto createdOwner = ownerService.createOwner(ownerDto);
        return new ResponseEntity<>(createdOwner, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public OwnerDto getOwnerById(@PathVariable Long id) {
        return ownerService.getOwnerById(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public List<OwnerDto> getAllOwners() {
        return ownerService.getAllOwners();
    }

    @PostMapping("/{ownerId}/cats/{catId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void addCatToOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        ownerService.addCatToOwner(ownerId, catId);
    }

    @DeleteMapping("/{ownerId}/cats/{catId}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public void removeCatFromOwner(@PathVariable Long ownerId, @PathVariable Long catId) {
        ownerService.removeCatFromOwner(ownerId, catId);
    }
}