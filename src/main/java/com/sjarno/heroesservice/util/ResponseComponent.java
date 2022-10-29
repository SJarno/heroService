package com.sjarno.heroesservice.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.sjarno.heroesservice.models.response.HeroServiceResponse;

@Component
public class ResponseComponent {

    public <T> ResponseEntity<?> populateResponse(T result, 
            HttpStatus httpStatus,
            String message, String error) {

        HeroServiceResponse<T> response = new HeroServiceResponse<>();
        response.setBody(result);
        response.setMessage(message);
        response.setError(error);
        response.setStatus(httpStatus.toString());

        // return response;
        return new ResponseEntity<>(response, httpStatus);
    }
}
