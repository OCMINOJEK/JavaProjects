package org.example.catservice.controllers;

import java.util.List;

import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.example.catservice.services.CatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cats")
public class CatController {

    private final CatService catService;

    @Autowired
    public CatController(CatService catService) {
        this.catService = catService;
    }

    @GetMapping("/{id}")
    public CatDto getCatById(@PathVariable Long id) {
        return catService.getCatById(id);
    }

    @GetMapping("/all-cats")
    public List<CatDto> getAllCats() {
        return catService.getAllCats();
    }

    @GetMapping("/owner/{id}")
    public List<CatDto> getAllCatsByOwnerId(@PathVariable Long id) {
        return catService.getAllCatsByOwnerId(id);
    }

    @PostMapping
    public ResponseEntity<CatDto> createCat(@RequestBody CatCreateDto catDto) {
        CatDto createdCat = catService.createCat(catDto);
        return new ResponseEntity<>(createdCat, HttpStatus.CREATED);
    }

    @PutMapping("/{catId}/friends/{friendId}")
    public void addFriendToCat(@PathVariable Long catId, @PathVariable Long friendId) {
        catService.addFriendToCat(catId, friendId);
    }

    @DeleteMapping("/{catId}/friends/{friendId}")
    public void removeFriendFromCat(@PathVariable Long catId, @PathVariable Long friendId) {
        catService.removeFriendFromCat(catId, friendId);
    }
}
