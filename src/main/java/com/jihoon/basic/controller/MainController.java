package com.jihoon.basic.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

// description: Controller - 레이어드 아키텍처 상의 프레젠테이션 영역 //
// description: 클라이언트로부터 요청(입력)을 받고 서비스 결과를 응답(출력)하는 영역 //

// description: @RestController - REST API 형식 Controller를 만들고자 할때 사용하는 어노테이션 //
// description: Response Body의 타입이 JSON 형태의 데이터를 반환 //
@RestController
// description: @ResquestMapping - Request의 URL 패턴에 따라 클래스 및 메소드를 결정하는 어노테이션 //
@RequestMapping("/")  // http://localhost:4000/**
// @RequestMapping("/main") http://localhost:4000/main/**
public class MainController {

    // http://localhost:4000/hello GET
    @RequestMapping(value="hello", method={RequestMethod.POST})
    public String hello() {
        return "Hello Spring framework!!";
    }
    
}
