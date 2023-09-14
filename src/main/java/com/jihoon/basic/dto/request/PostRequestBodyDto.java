package com.jihoon.basic.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// description: DTO (Data Transfer Object) : 서로 다른 레이어 간에 데이터를 전송하고자 할때 사용하는 객체 //

// description: Lombok 라이브러리 - 클래스 생성시에 반복적으로 작성하는 메서드를 간편하게 생성해주는 라이브러리 //
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestBodyDto {
    private String name;
    private Integer age;
}
