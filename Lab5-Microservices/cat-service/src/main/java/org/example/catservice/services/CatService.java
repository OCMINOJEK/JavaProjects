package org.example.catservice.services;

import java.util.List;
import java.util.stream.Collectors;

import org.example.catservice.repositories.CatRepository;
import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.example.common.mapping.CatMapper;
import org.example.common.model.Cat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class CatService {
    private final CatRepository catRepository;

    @Autowired
    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
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
