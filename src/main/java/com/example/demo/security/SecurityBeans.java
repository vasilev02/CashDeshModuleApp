package com.example.demo.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * SecurityBeans class  is used to make beans which we are using with only one instance through our whole life of application.
 */
@Configuration
public class SecurityBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
