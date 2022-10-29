package com.sjarno.heroesservice.exceptions;

import lombok.Getter;

public class HeroNotFoundException extends RuntimeException {

    @Getter
    private final String clientErrorName = "Hero Service error";

    public HeroNotFoundException() {
        super("Hero not found");
    }

    public HeroNotFoundException(String message) {
        super(message);
    }

}
