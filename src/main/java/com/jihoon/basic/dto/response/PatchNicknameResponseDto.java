package com.jihoon.basic.dto.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@NoArgsConstructor
public class PatchNicknameResponseDto extends ResponseDto {
    
    public PatchNicknameResponseDto (String code, String message) {
        super(code, message);
    }

}
