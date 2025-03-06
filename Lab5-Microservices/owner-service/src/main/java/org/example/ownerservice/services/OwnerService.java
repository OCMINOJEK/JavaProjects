package org.example.ownerservice.services;

import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

import org.example.common.dto.CatDto;
import org.example.common.dto.OwnerCreateDto;
import org.example.common.dto.OwnerDto;
import org.example.common.mapping.CatMapper;
import org.example.common.mapping.OwnerMapper;
import org.example.common.model.Cat;
import org.example.common.model.Owner;
import org.example.ownerservice.messaging.CatLink;
import org.example.ownerservice.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {
    private final OwnerRepository ownerRepository;
    private final CatLink catLink;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, CatLink catLink) {
        this.ownerRepository = ownerRepository;
        this.catLink = catLink;
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

    public List<OwnerDto> getAllOwners() {
        List<Owner> owners = this.ownerRepository.findAll();
        return owners.stream().map(OwnerMapper::toDto).collect(Collectors.toList());
    }

    public void addCatToOwner(Long ownerId, Long catId) {
        Owner owner = this.ownerRepository.findById(ownerId).orElseThrow(() -> {
            return new EntityNotFoundException("Owner not found");
        });
        CatDto catDto = catLink.findById(catId);
        if (catDto == null) {
            throw new EntityNotFoundException("Cat not found");
        }
        Cat cat = CatMapper.toCat(catDto);
        owner.getCats().add(cat);
        this.ownerRepository.save(owner);
    }

    public void removeCatFromOwner(Long ownerId, Long catId) {
        Owner owner = this.ownerRepository.findById(ownerId).orElseThrow(() -> {
            return new EntityNotFoundException("Owner not found");
        });
        CatDto catDto = catLink.findById(catId);
        if (catDto == null) {
            throw new EntityNotFoundException("Cat not found");
        }
        Cat cat = CatMapper.toCat(catDto);
        owner.getCats().remove(cat);
        this.ownerRepository.save(owner);
    }
}
