
package org.example.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;


@Getter
@Setter
public class OwnerDto {
    private Long id;
    private Long userId;
    private String name;
    private LocalDate birthDate;
    private List<CatDto> cats;
}
