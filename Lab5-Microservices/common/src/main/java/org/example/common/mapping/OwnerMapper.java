
package org.example.common.mapping;

import java.util.List;
import java.util.stream.Collectors;

import org.example.common.dto.OwnerDto;
import org.example.common.model.Owner;
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

    public static Owner toOwner(OwnerDto ownerDto) {
        Owner owner = new Owner();
        owner.setName(ownerDto.getName());
        owner.setBirthDate(ownerDto.getBirthDate());
        return owner;
    }
}
