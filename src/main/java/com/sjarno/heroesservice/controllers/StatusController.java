package com.sjarno.heroesservice.controllers;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sjarno.heroesservice.util.ResponseComponent;

@CrossOrigin("*")
@RestController
public class StatusController {

    @Value("${mainTitle.title}")
    private String title;

    @Autowired
    private ResponseComponent responseComponent;

    @GetMapping(value = "/status")
    public ResponseEntity<?> getStatus() {
        Map<String, String> status = new HashMap<>();
        status.put("status", "ok");
        return this.responseComponent.populateResponse(
                status, HttpStatus.OK, "Fetching status", "");
    }

    @GetMapping("/main-title")
    public ResponseEntity<?> getMainTitle() {
        return this.responseComponent.populateResponse(title, HttpStatus.OK, "Fetching main title", "");
    }
    

}
