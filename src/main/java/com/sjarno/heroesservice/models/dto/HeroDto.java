package com.sjarno.heroesservice.models.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroDto {
    private Long id;
    @NotBlank
    @Size(min = 1, max = 20)
    private String name;
}
