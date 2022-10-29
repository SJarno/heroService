package com.sjarno.heroesservice.models.response;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HeroServiceResponse<T> {
    private final String type = "Hero Service Responses";
    private Date date = new Date();
    private String status;
    private String message;
    private T body;
    private String error;
}
