package com.jihoon.basic.service;

import org.springframework.http.ResponseEntity;

import com.jihoon.basic.dto.request.PatchNicknameRequestDto;
import com.jihoon.basic.dto.request.PostUserRequestDto;
import com.jihoon.basic.dto.response.PatchNicknameResponseDto;
import com.jihoon.basic.dto.response.PostUserResponseDto;

public interface MainService {
    
    String getMethod();
    ResponseEntity<? super PostUserResponseDto> postUser(PostUserRequestDto dto);
    ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto);

}
