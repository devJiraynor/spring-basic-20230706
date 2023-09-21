package com.jihoon.basic.service.implement;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jihoon.basic.dto.request.PatchNicknameRequestDto;
import com.jihoon.basic.dto.request.PostUserRequestDto;
import com.jihoon.basic.dto.response.DeleteUserResponseDto;
import com.jihoon.basic.dto.response.PatchNicknameResponseDto;
import com.jihoon.basic.dto.response.PostUserResponseDto;
import com.jihoon.basic.dto.response.ResponseDto;
import com.jihoon.basic.entity.UserEntity;
import com.jihoon.basic.repository.UserRepository;
import com.jihoon.basic.service.MainService;

import lombok.RequiredArgsConstructor;

// description: @Component - 해당 클래스를 Java bean으로 등록하여 Spring이 인스턴스 생성을 알아서 할 수 있도록 하는 어노테이션 //
// description: @Service - @Componenet와 동일한 작업을 수행하지만 가독성을 위해 Service라는 이름을 가짐 //
@Service
@RequiredArgsConstructor
public class MainServiceImplement implements MainService {

    private final UserRepository userRepository;

    // description: PasswordEncoder - 비밀번호를 안전하게 암호화하고 검증하는 인터페이스 //
    // description: BCryptPasswordEncoder - Bcrypt 해시 알고리즘을 사용하는 PasswordEncoder 구현 클래스 //
    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public String getMethod() {
        return "This method is GET method.";
    }

    @Override
    public ResponseEntity<? super PostUserResponseDto> postUser(PostUserRequestDto dto) {
        
        // INSERT INTO user(email, password, nickname, tel_numnber, address, address_detail)
        // VALUES(dto.getEmail(), dto.getPassword(), ...);

        try {
            // description: 비밀번호 암호화 작업 //
            // description: 1. dto로부터 평문의 비밀번호(암호화할 문자열)를 가져옴 //
            String password = dto.getPassword();
            // description: 2. PasswordEncoder의 인스턴스의 encode() 메서드로 평문을 암호화 //
            String encodedPassword = passwordEncoder.encode(password);
            // description: 3. dto에 다시 주입 //
            dto.setPassword(encodedPassword);

            // description: Create 작업 순서 (INSERT) //
            // description: 1. Entity 인스턴스 생성 //
            UserEntity userEntity = new UserEntity(dto);
            // description: 2. repository의 save 메서드 사용 //
            userRepository.save(userEntity);
        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DBE", "Database Error"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new PostUserResponseDto("SU", "SUCCESS"));
    }

    @Override
    public ResponseEntity<? super PatchNicknameResponseDto> patchNickname(PatchNicknameRequestDto dto) {
        
        // UPDATE user SET nickname = dto.getNickname() WHERE email = dto.getEmail();

        try {

            // description: Update 작업 순서 (UPDATE) //
            // description: 1. Entity 인스턴스 조회 //
            // description: findById() - primary key를 사용하여 레코드를 검색하는 메서드 //
            // SELECT * FROM user WHERE email = ??;
            UserEntity userEntity = userRepository.findById(dto.getEmail()).get();

            // description: 2. Entity 인스턴스 수정 //
            userEntity.updateNickname(dto.getNickname());

            // description: 3. repository의 save 메서드 사용 //
            // description: save() - Entity 객체를 테이블에 저장하는 메서드 //
            // description: 만약 해당 인스턴스의 ID 값과 동일한 레코드가 존재하면 해당 레코드를 수정 //
            // description: 그렇지 않다면 레코드를 생성 //
            userRepository.save(userEntity);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DBE", "Database Error"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new PatchNicknameResponseDto("SU", "SUCCESS"));

    }

    @Override
    public ResponseEntity<? super DeleteUserResponseDto> deleteUser(String email) {
        
        // DELETE FROM user WHERE email = email;

        try {

            // description: Delete 작업 순서 (DELETE) //
            // description: 1. repository의 deleteById 메서드 사용 //
            userRepository.deleteById(email);

        } catch (Exception exception) {
            exception.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDto("DBE", "Database Error"));
        }

        return ResponseEntity.status(HttpStatus.OK).body(new DeleteUserResponseDto("SU", "SUCCESS"));

    }
    
}
