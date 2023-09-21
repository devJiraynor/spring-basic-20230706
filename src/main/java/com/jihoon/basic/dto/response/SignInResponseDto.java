package com.jihoon.basic.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignInResponseDto extends ResponseDto {

    private String token;
    private int expiration;
    
    public SignInResponseDto(String code, String message, String token) {
        super(code, message);
        this.token = token;
        this.expiration = 3600;
    }

}
