package com.jihoon.basic.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sub")
public class SubController {
    
    @GetMapping("/method1")
    public String method1() {
        return "/sub/method1 입니다.";
    }

}
