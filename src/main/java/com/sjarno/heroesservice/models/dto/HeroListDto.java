package com.sjarno.heroesservice.models.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroListDto {
    private List<HeroDto> heroes;
}
