package com.cos.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에서 UsernamePasswordAuthenticationFilter 가 있다.
// UsernamePasswordAuthenticationFilter 필터는 /login 요청해서 username,password 전송하면 (POST) 동작함
//하지만 securityConfig 파일에서 .formLogin().disable() 를 선언했기떄문에 지금 동작하지 않는상태인데
// 이 필터가 동작하게하기위해서는 SecurityConfig 파일에서 add필터로 추가해주면 된다.

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private  final AuthenticationManager authenticationManager;

    // /login 요청을 하면 로그인 시도를 위해서 실행되는 메서드
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("JwtAuthenticationFilter:로그인 시도중");

        //1. username ,password 받아서

        //2. 정상인지 로그인 시도를 해본다. authenticationManager로 로그인 시도를 하면
        // PrincipalDetailsServics가 호출되고 loadUserByUsername()메서드 호출됨.

        //3.PrincipalDetails를 세션에 담고( 권한 관리를 위해서 )

        //4.JWT 토큰을 만들어서 응답해주면 됨.
        return super.attemptAuthentication(request, response);
    }
}
