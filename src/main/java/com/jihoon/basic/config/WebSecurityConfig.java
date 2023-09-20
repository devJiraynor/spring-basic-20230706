package com.jihoon.basic.config;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.jihoon.basic.filter.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;

// description: 인증 및 인가 처리와 관련된 여러 설정을 지정하는 클래스 //

// description: @Configurable - Spring 설정 변경이 가능한 클래스로 지정하는 어노테이션 //
@Configurable
// description: @EnableWebSecurity - Spring Security 설정 변경 클래스로 지정하는 어노테이션 //
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // description: Security 설정 변경 메서드 작성 //
    @Bean
    protected SecurityFilterChain configure(HttpSecurity httpSecurity) throws Exception {

        httpSecurity
            // description: CORS 정책은 기본 정책으로 사용 (CorsConfig를 따르게 함) //
            .cors().and()
            // description: CSRF 보안 설정은 사용하지 않음 //
            .csrf().disable()
            // description: basic 인증 사용하지 않음 //
            .httpBasic().disable()
            // description: 세션 생성 전략을 세션을 생성하지 않음으로 설정 //
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            // description: 어떤 요청에 대해서 인증을 수행할지 지정하는 설정 //
            .authorizeRequests()
            // description: antMatcher() - 특정 요청 지정 (url 패턴에 따른 지정, http method에 따른 지정, http method + url 패턴에 따른 지정) //
            // description: url 패턴에 따른 지정 (/user 로 시작하는 모든 요청에 대하여 허용) //
            .antMatchers("/user/**").permitAll()
            // description: http method에 따른 지정 (모든 GET 요청에 대해서 허용) //
            .antMatchers(HttpMethod.GET).permitAll()
            // description: http method + url 패턴에 따른 지정 (POST /board 로 시작하는 모든 요청에 대하여 허용) //
            .antMatchers(HttpMethod.POST, "/board/**").permitAll()
            // description: 나머지 모든 요청에 대하여 인증을 수행 //
            .anyRequest().authenticated();

        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();
    }
    
}
