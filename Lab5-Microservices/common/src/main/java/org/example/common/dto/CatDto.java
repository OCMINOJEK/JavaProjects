
package org.example.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CatDto {
    private Long Id;
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;

}
