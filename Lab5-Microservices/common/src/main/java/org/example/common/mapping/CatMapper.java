package org.example.common.mapping;


import org.example.common.dto.CatCreateDto;
import org.example.common.dto.CatDto;
import org.example.common.model.Cat;
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
    public static Cat toCat(CatDto catDto) {
        Cat cat = new Cat();
        cat.setName(catDto.getName());
        cat.setBirthDate(catDto.getBirthDate());
        cat.setBreed(catDto.getBreed());
        cat.setColor(catDto.getColor());
        return cat;
    }
}
