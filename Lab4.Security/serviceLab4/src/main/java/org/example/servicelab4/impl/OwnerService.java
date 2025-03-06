//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.servicelab4.impl;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import org.example.repositorylab4.api.CatRepository;
import org.example.repositorylab4.api.OwnerRepository;
import org.example.repositorylab4.dto.OwnerCreateDto;
import org.example.repositorylab4.dto.OwnerDto;
import org.example.repositorylab4.model.Cat;
import org.example.repositorylab4.model.Owner;
import org.example.servicelab4.mapping.OwnerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final CatRepository catRepository;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, CatRepository catRepository) {
        this.ownerRepository = ownerRepository;
        this.catRepository = catRepository;
    }

    public OwnerDto createOwner(OwnerCreateDto ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        owner = this.ownerRepository.save(owner);
        return OwnerMapper.toDto(owner);
    }

    public OwnerDto getOwnerById(Long id) {
        Owner owner = this.ownerRepository.findById(id).orElseThrow(() -> {
            return new EntityNotFoundException("Owner not found");
        });
        return OwnerMapper.toDto(owner);
    }

    public List getAllOwners() {
        List<Owner> owners = this.ownerRepository.findAll();
        return owners.stream().map(OwnerMapper::toDto).collect(Collectors.toList());
    }

    public void addCatToOwner(Long ownerId, Long catId) {
        Owner owner = this.ownerRepository.findById(ownerId).orElseThrow(() -> {
            return new EntityNotFoundException("Owner not found");
        });
        Cat cat = this.catRepository.findById(catId).orElseThrow(() -> {
            return new EntityNotFoundException("Cat not found");
        });
        owner.getCats().add(cat);
        this.ownerRepository.save(owner);
    }

    public void removeCatFromOwner(Long ownerId, Long catId) {
        Owner owner = (Owner)this.ownerRepository.findById(ownerId).orElseThrow(() -> {
            return new EntityNotFoundException("Owner not found");
        });
        Cat cat = (Cat)this.catRepository.findById(catId).orElseThrow(() -> {
            return new EntityNotFoundException("Cat not found");
        });
        owner.getCats().remove(cat);
        this.ownerRepository.save(owner);
    }
}
