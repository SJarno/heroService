package com.sjarno.heroesservice.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sjarno.heroesservice.exceptions.HeroNotFoundException;
import com.sjarno.heroesservice.models.dto.HeroDto;
import com.sjarno.heroesservice.models.dto.HeroListDto;
import com.sjarno.heroesservice.models.entities.HeroEntity;
import com.sjarno.heroesservice.repositories.HeroRepository;

/**
 * Service layer to handle business logic, mapping etc
 */
@Service
public class HeroService {

    @Autowired
    private HeroRepository heroRepository;

    @Autowired
    ModelMapper mapper;

    public HeroListDto getAllHeroes() {
        List<HeroEntity> heroes = this.heroRepository.findAll();
        return this.mapHeroes(heroes);

    }

    public HeroDto getHeroById(Long id) {
        HeroEntity heroEntity = this.getHeroEntityById(id);
        return mapper.map(heroEntity, HeroDto.class);
    }

    @Transactional
    public HeroDto updateHero(HeroDto heroDto) {
        HeroEntity heroEntity = this.getHeroEntityById(heroDto.getId());
        heroEntity.setName(heroDto.getName());

        return mapper.map(heroEntity, HeroDto.class);
    }

    public HeroDto createNewHero(HeroDto heroDto) {
        HeroEntity newHero = new HeroEntity();
        newHero.setName(heroDto.getName());
        // HeroEntity created = this.heroRepository.save(newHero);

        return mapper.map(this.heroRepository.save(newHero), HeroDto.class);
    }

    public HeroDto deleteHeroById(Long id) {
        HeroDto heroDto = mapper.map(this.getHeroById(id), HeroDto.class);
        this.heroRepository.deleteById(heroDto.getId());
        return heroDto;
    }

    public HeroListDto searchByName(String name) {
        List<HeroEntity> heroEntities = this.heroRepository.findByNameContainingIgnoreCase(name);
        return this.mapHeroes(heroEntities);

    }

    private HeroEntity getHeroEntityById(Long id) {
        return this.heroRepository.findById(id)
                .orElseThrow(() -> new HeroNotFoundException(String.format("Hero with id %s was not found", id)));
    }
    private HeroListDto mapHeroes(List<HeroEntity> heroEntities) {
        return new HeroListDto() {{
            setHeroes(heroEntities.stream()
                .map(h -> mapper.map(h, HeroDto.class))
                .collect(Collectors.toList()));
        }};
    }

    public void initDatabase() {
        List<HeroEntity> heroes = new ArrayList<>() {
            {
                add(new HeroEntity(11L, "Dr. Nice"));
                add(new HeroEntity(12L, "Bombasto"));
                add(new HeroEntity(13L, "Celeritas"));
                add(new HeroEntity(14L, "Magneta"));
                add(new HeroEntity(15L, "RubberMan"));
                add(new HeroEntity(16L, "Dynama"));
                add(new HeroEntity(17L, "Dr. IQ"));
                add(new HeroEntity(18L, "Magma"));
                add(new HeroEntity(19L, "Windstorm"));
                add(new HeroEntity(20L, "Tornado"));
            }
        };
        this.heroRepository.saveAll(heroes);
    }

}
