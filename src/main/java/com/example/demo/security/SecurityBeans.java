package com.example.demo.security;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityBeans {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
