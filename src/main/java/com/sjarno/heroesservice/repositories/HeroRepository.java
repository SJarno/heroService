package com.sjarno.heroesservice.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sjarno.heroesservice.models.entities.HeroEntity;

public interface HeroRepository extends JpaRepository<HeroEntity, Long> {
    List<HeroEntity> findByNameContainingIgnoreCase(String name);
}
