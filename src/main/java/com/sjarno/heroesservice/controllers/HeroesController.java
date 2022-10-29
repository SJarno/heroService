package com.sjarno.heroesservice.controllers;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.sjarno.heroesservice.models.dto.HeroDto;
import com.sjarno.heroesservice.models.dto.HeroListDto;
import com.sjarno.heroesservice.services.HeroService;
import com.sjarno.heroesservice.util.ResponseComponent;

@RequestMapping("api/rest/heroes")
@RestController
public class HeroesController {

    @Autowired
    private HeroService heroService;

    @Autowired
    private ResponseComponent responseComponent;

    @GetMapping("/all-heroes")
    public ResponseEntity<?> getAllHeroes() {
        HeroListDto heroes = this.heroService.getAllHeroes();
        return this.responseComponent.populateResponse(heroes, HttpStatus.OK, "Heroes found!", null);

    }

    @GetMapping("/hero-by-id/{id}")
    public ResponseEntity<?> getHeroById(@PathVariable Long id) {
        HeroDto hero = this.heroService.getHeroById(id);
        return this.responseComponent.populateResponse(hero, HttpStatus.OK, "Hero found!", null);

    }
    @PutMapping(path = "/update")
    public ResponseEntity<?> updateHero(@Valid @RequestBody HeroDto heroDto) {
        HeroDto updatedHero = this.heroService.updateHero(heroDto);
        return this.responseComponent.populateResponse(updatedHero, HttpStatus.CREATED, "Hero updated", null);
    }
    @PostMapping("/create")
    public ResponseEntity<?> createHero(@Valid @RequestBody HeroDto heroDto) {
        HeroDto createdHero = this.heroService.createNewHero(heroDto);
        return this.responseComponent.populateResponse(createdHero, HttpStatus.CREATED, "Hero created", null);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteHeroById(@PathVariable Long id) {
        HeroDto deletedHero = this.heroService.deleteHeroById(id);
        return this.responseComponent.populateResponse(deletedHero, HttpStatus.OK, "Hero deleted", null);
    }
    @GetMapping("/heroes-by-name")
    public ResponseEntity<?> getHeroesByName(@RequestParam String name) {
        HeroListDto heroes = this.heroService.searchByName(name);
        return this.responseComponent.populateResponse(heroes, HttpStatus.OK, "Heroes by name", null);
    }

    @PostConstruct
    public void init() {
        this.heroService.initDatabase();
    }

}
