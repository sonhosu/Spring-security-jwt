package com.cos.jwt;

import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;

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
        try {
           /* BufferedReader br = request.getReader();
            String input = null;
            while ((input = br.readLine()) != null) {

                System.out.println(input);
            }*/

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream() , User.class);
            System.out.println("User:"+user);
            System.out.println(request.getInputStream().toString());

            //토큰 생성
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());

            //PrincipalDetails의 loadUserByUsername()함수가 실행된후 정상이면 authentication 이 리턴된다.
            //DB에 있는 username과 password가 일치한다
            Authentication authentication = authenticationManager.authenticate(authenticationToken);


            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            //로그인이 되었다는 뜻.
            System.out.println("로그인 완료"+principalDetails.getUser().getUsername());
            //authentication 객체가 session 영역에 저장을 해야하고 그방법이 return해주면 됨 >
            //리턴의 이유는 권한 관리를 security가 대신 해주기 때문에 편하기위해 하는거임
            //굳이 JWT 를 토큰을 사용하면서 세션을 만들이유가없다. 단지 권한처리때문에 session을 만들어준다


            return authentication;
        } catch (IOException e) {
           e.printStackTrace();
        }
        System.out.println("============================");
        return null;
    }

    // attemptAuthentication 실행후 인증이 정상적으로 되었으면 successfulAuthentication 메서드가 실행된다
    // 여기서 JWT 토큰을 만들어서 request요청한 사용자에게 JWT 토큰을 response 해주면 된다.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        System.out.println("successfulAuthentication 실행됨 : 인증완료");

        super.successfulAuthentication(request, response, chain, authResult);
    }
}
