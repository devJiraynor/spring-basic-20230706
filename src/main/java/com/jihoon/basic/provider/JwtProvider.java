package com.jihoon.basic.provider;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// description: JWT를 생성 및 검증해주는 클래스 //
public class JwtProvider {

    private String secretKey = "S3cr3tk3y";

    // description: JWT 생성 메서드 //
    public String create(String subject) {

        // description: 토큰 만료 시간 (현재시간으로부터 1시간 후) //
        Date expiration = Date.from(Instant.now().plus(1, ChronoUnit.HOURS));

        // description: JWT 생성 //
        // description: 1. Jwts 클래스의 builder() 메서드를 통해서 작성을 시작 //
        String jwt = Jwts.builder()
        // description: 2. signWith() 메서드를 통해서 서명 알고리즘 및 서명에 사용할 비밀키 지정 //
                        .signWith(SignatureAlgorithm.HS256, secretKey)
        // description: 3. setXXX() 메서드를 통해서 payload 작성 //
        // description: setSubject() - 생성 주체 (접근 주체), setIssuedAt() - 토큰 생성 시간, setExpiration() - 토큰 만료 시간 //
                        .setSubject(subject).setIssuedAt(new Date()).setExpiration(expiration)
        // description: 4. compact() 메서드를 통해서 jwt 생성 완료 //
                        .compact();

        return jwt;
    }

    // description: JWT 검증 메서드 //
    // description: 검증 결과 과정 //
    // description: 1. jwt를 받아옴 //
    // description: 2. 받아온 jwt를 우리가 알고있는 비밀키로 검증 //
    // description: 3. 검증 완료 후 jwt에서 payload를 꺼내옴 //
    // description: 4. payload에서 원하는 데이터(subject)를 반환 //
    public String validate(String jwt) {

        Claims claims = null;

        try {
            // description: Jwts 클래스의 parser() 메서드를 통해서 파싱 시작 //
            claims = Jwts.parser()
            // description: setSignKey() 메서드를 통해서 parser에 비밀키 등록하여 검증 //
                        .setSigningKey(secretKey)
            // description: parseClaimsJws() 메서드를 통해서 파싱 처리 //
                        .parseClaimsJws(jwt)
            // description: getBody() 메서드를 통해서 클레임(페이로드)를 꺼내옴 //
                        .getBody();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }

        if (claims == null) return null;
        
        // description: getXXX() 메서드를 통해서 원하는 정보 가져옴 //
        String subject = claims.getSubject();

        return subject;
    }
    
}
