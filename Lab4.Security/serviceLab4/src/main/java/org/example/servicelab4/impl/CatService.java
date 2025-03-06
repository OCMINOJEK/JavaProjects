//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package org.example.servicelab4.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.example.repositorylab4.api.CatRepository;
import org.example.repositorylab4.api.OwnerRepository;
import org.example.repositorylab4.dto.CatCreateDto;
import org.example.repositorylab4.dto.CatDto;
import org.example.repositorylab4.model.Cat;
import org.example.servicelab4.mapping.CatMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    private final CatRepository catRepository;
    private final OwnerRepository ownerRepository;

    @Autowired
    public CatService(CatRepository catRepository, OwnerRepository ownerRepository) {
        this.catRepository = catRepository;
        this.ownerRepository = ownerRepository;
    }

    public CatDto createCat(CatCreateDto catDto) {
        Cat cat = new Cat();
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        cat = this.catRepository.save(cat);
        return CatMapper.toDto(cat);
    }

    public CatDto getCatById(Long id) {
        Cat cat = this.catRepository.findById(id).orElseThrow(() -> {
            return new RuntimeException("Cat not found");
        });
        return CatMapper.toDto(cat);
    }

    public List<CatDto> getAllCats() {
        List<Cat> cats = this.catRepository.findAll();
        return cats.stream().map(CatMapper::toDto).collect(Collectors.toList());
    }

    public List<CatDto> getAllCatsByOwnerId(Long ownerId) {
        List<Cat> cats = this.catRepository.findAllByOwnerId(ownerId);
        return cats.stream().map(CatMapper::toDto).collect(Collectors.toList());
    }

    public void addFriendToCat(Long catId, Long friendId) {
        Cat cat = this.catRepository.findById(catId).orElseThrow(() -> {
            return new RuntimeException("Cat not found");
        });
        Cat friend = this.catRepository.findById(friendId).orElseThrow(() -> {
            return new RuntimeException("Friend cat not found");
        });
        cat.getFriends().add(friend);
        this.catRepository.save(cat);
    }

    public void removeFriendFromCat(Long catId, Long friendId) {
        Cat cat = this.catRepository.findById(catId).orElseThrow(() -> {
            return new RuntimeException("Cat not found");
        });
        Cat friend = this.catRepository.findById(friendId).orElseThrow(() -> {
            return new RuntimeException("Friend cat not found");
        });
        cat.getFriends().remove(friend);
        this.catRepository.save(cat);
    }
}
