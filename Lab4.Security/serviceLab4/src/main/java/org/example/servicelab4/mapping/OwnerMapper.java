
package org.example.servicelab4.mapping;

import java.util.List;
import java.util.stream.Collectors;
import org.example.repositorylab4.dto.CatDto;
import org.example.repositorylab4.dto.OwnerCreateDto;
import org.example.repositorylab4.dto.OwnerDto;
import org.example.repositorylab4.model.Cat;
import org.example.repositorylab4.model.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {
    public OwnerMapper() {
    }

    public static OwnerDto toDto(Owner owner) {
        OwnerDto ownerDto = new OwnerDto();
        ownerDto.setId(owner.getId());
        ownerDto.setName(owner.getName());
        ownerDto.setBirthDate(owner.getBirthDate());
        ownerDto.setCats((List)owner.getCats().stream().map(CatMapper::toDto).collect(Collectors.toList()));
        return ownerDto;
    }

    public static CatDto catToDto(Cat cat) {
        CatDto catDto = new CatDto();
        catDto.setName(cat.getName());
        catDto.setBirthDate(cat.getBirthDate());
        catDto.setBreed(cat.getBreed());
        catDto.setColor(cat.getColor());
        return catDto;
    }

    public OwnerDto toOwner(OwnerCreateDto ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        return OwnerMapper.toDto(owner);
    }
}
