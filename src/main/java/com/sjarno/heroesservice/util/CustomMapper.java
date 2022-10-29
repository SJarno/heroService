package com.sjarno.heroesservice.util;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMapper {
    
   @Bean
   public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    return modelMapper;
   }

}
