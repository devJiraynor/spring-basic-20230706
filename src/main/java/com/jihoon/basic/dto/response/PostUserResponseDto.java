package com.jihoon.basic.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostUserResponseDto extends ResponseDto {

    public PostUserResponseDto(String code, String message) {
        super(code, message);
    }
    
}
