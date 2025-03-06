package org.example.repositorylab4.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class CatCreateDto {
    private String name;
    private LocalDate birthDate;
    private String breed;
    private String color;
    private Long ownerId;
}
