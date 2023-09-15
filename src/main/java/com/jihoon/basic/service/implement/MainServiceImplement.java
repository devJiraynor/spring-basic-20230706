package com.jihoon.basic.service.implement;

import org.springframework.stereotype.Component;

import com.jihoon.basic.service.MainService;

@Component
public class MainServiceImplement implements MainService {

    @Override
    public String getMethod() {
        return "This method is GET method.";
    }
    
}
