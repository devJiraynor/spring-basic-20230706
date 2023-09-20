package com.jihoon.basic.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.jihoon.basic.provider.JwtProvider;

import lombok.RequiredArgsConstructor;

// description: Bearer Token 인증 방식을 사용한 JWT 인증 필터 //
// description: 역할 : Request Header의 Authorization 필드의 값을 가져와서 //
// description:        해당 토큰이 정상적인 토큰인지 확인하고 정상이 아닐 경우 요청을 거부 //
// description:        정상적인 토큰일 경우 인증된 사용자의 정보를 Controller에서 사용할 수 있도록 함 //

// description: OncePerRequestFilter를 확장하여 해당 클래스를 Filter 클래스로 만듬 //
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;

    // description: OncePerRequestFilter의 doFilterInternal 추상 메서드에 해당 필터에서 동작할 기능을 구현 //
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // description: 1. Request의 Header에 있는 Authorization에서 JWT 추출 //
            String token = parseBearerToken(request);
            if (token == null) {
                // description: 다음 필터로 넘김 //
                filterChain.doFilter(request, response);
                return;
            }
            // description: 2. 추출한 token 검증 //
            String subject = jwtProvider.validate(token);
            if (subject == null) {
                filterChain.doFilter(request, response);
                return;
            }
            // description: 3. Context에 등록할 토큰 객체 생성 //
            AbstractAuthenticationToken authenticationToken
                // description: UsernamePasswordAuthenticationToken - 사용자이름, 패스워드, 권한으로 구성되어 있는 토큰 객체 //
                = new UsernamePasswordAuthenticationToken(subject, null, AuthorityUtils.NO_AUTHORITIES);
            // description: 4. 인증 토큰에 어떤 요청에 대한 인증 토큰인지 정보를 저장 //
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            // description: 5. 빈 인증 보안 컨텍스트 생성 //
            SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
            // description: 6. 생성한 컨텍스트에 인증 토큰 등록 //
            securityContext.setAuthentication(authenticationToken);
            // description: 7. 생성한 컨텍스트를 컨텍스트로 등록 //
            SecurityContextHolder.setContext(securityContext);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
        
    }
    
    // description: Request 객체로부터 JWT 추출하는 메서드 //
    private String parseBearerToken (HttpServletRequest request) {

        // description: 1. Request 객체의 Header에서 Authorization 값을 추출 //
        String authorization = request.getHeader("Authorization");
        
        // description: StringUtils.hasText() - 전달한 문자열이 null 이거나 빈문자열이거나 공백으로만 구성되어 있으면 false 반환 //
        boolean hasAuthorization = StringUtils.hasText(authorization);
        if (!hasAuthorization) return null;

        // description: 2. 인증 방식이 Bearer 방식인지 확인 //
        boolean isBearer = authorization.startsWith("Bearer ");
        if (!isBearer) return null;

        // description: 3. token 추출 //
        String token = authorization.substring(7);

        return token;

    }

}
