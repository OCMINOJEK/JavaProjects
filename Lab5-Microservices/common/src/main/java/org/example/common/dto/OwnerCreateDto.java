package org.example.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class OwnerCreateDto {
    private String name;
    private LocalDate birthDate;
}