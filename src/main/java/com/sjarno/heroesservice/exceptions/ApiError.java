package com.sjarno.heroesservice.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiError {
    private HttpStatus status;
    private String serviceName;
    private String requestDetails;
    private String message;
    private List<String> errors;
}
