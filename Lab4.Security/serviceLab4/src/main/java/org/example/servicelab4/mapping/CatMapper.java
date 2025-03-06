package org.example.servicelab4.mapping;

import org.example.repositorylab4.dto.CatCreateDto;
import org.example.repositorylab4.dto.CatDto;
import org.example.repositorylab4.dto.OwnerDto;
import org.example.repositorylab4.model.Cat;
import org.springframework.stereotype.Component;

@Component
public class CatMapper {
    public CatMapper() {
    }

    public static CatDto toDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setId(cat.getId());
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        return catDto;
    }
    public CatDto toCat(CatCreateDto catDto) {
        Cat cat = new Cat();
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        return CatMapper.toDto(cat);
    }
}
