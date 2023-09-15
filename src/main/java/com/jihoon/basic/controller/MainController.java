package com.jihoon.basic.controller;

import javax.validation.Valid;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jihoon.basic.dto.request.PatchValidationDto;
import com.jihoon.basic.dto.request.PostRequestBodyDto;

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

    // description: @RequestMapping 중 GET Method에 한정하여 인식 //
    // description: 데이터를 얻기 위한 요청 //
    // description: 데이터 입력시 URL로 입력 //
    @GetMapping("")
    public String getMethod() {
        return "This method is Get Method";
    }

    // description: @RequestMapping 중 Post Method에 한정하여 인식 //
    // description: 데이터를 생성하기 위한 요청 //
    // description: 데이터 입력시 Body로 입력 //
    @PostMapping("")
    public String postMethod() {
        return "This method is Post Method";
    }

    // description: @RequestMapping 중 PUT Method에 한정하여 인식 //
    // description: 데이터를 수정하기 위한 요청 (전체를 수정) //
    // description: 데이터 입력시 Body로 입력 //
    @PutMapping("")
    public String putMethod() {
        return "This mehtod is Put method";
    }

    // description: @RequestMapping 중 PATCH Method에 한정하여 인식 //
    // description: 데이터를 수정하기 위한 작업 (일부만 수정) //
    // description: 데이터 입력시 Body로 입력 //
    @PatchMapping("")
    public String patchMethod() {
        return "This method is Patch method";
    }

    // description: @RequestMapping 중 Delete Method에 한정하여 인식 //
    // description: 데이터를 삭제하기 위한 작업 //
    // description: 데이터 입력시 URL로 입력 //
    @DeleteMapping("")
    public String deleteMethod() {
        return "This method is Delete method";
    }

    // description: @PathVariabale - Path 자체를 변수의 값으로 인식 //
    // description: {변수명}로 표현 -> @PathVariable("변수명")로 받음 //
    @GetMapping("path-variable/{variable}")
    public String getPathVariable(
        @PathVariable("variable") String variable
    ) {
        return "Parameter value : " + variable;
    }

    // description: @RequestParam - Query Parameter로 Key와 Value를 받아옴 //
    // description: Query Parameter - ?name1=value1&name2=value2... //
    // description: @RequestParam("name1") -> name1에 대한 value1를 받음 //
    @GetMapping("parameter")
    public String getParameter(
        @RequestParam("name") String name,
        @RequestParam("age") Integer age
    ) {
        return "name: " + name + ", age: " + age;
    }

    // description: @RequestBody - Request의 Body에 포함된 데이터를 받아옴 //
    // description: 문자열 혹은 객체로 받을 수 있음 //
    @PostMapping("request-body")
    public String postRequestBody(
        // @RequestBody String requestBody
        @RequestBody PostRequestBodyDto requestBody
    ) {
        return "Request의 Body는 " + requestBody.getName() + " " + requestBody.getAge() + " 입니다.";
    }

    @PatchMapping("validation")
    public String validation(
        @RequestBody @Valid PatchValidationDto requestBody
    ) {
        return requestBody.getArg1();
    }
    
}
