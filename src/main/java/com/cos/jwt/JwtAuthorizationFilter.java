package com.cos.jwt;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cos.jwt.auth.PrincipalDetails;
import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//시큐리티가 filter를 가지고 있는데 그필터중에 BasicAuthenticationFilter 라는거시 있다.
//권한이나 인증이 필요한 특정 주소를 요청했을때 위 필터를 무조건 타게 되어있다.
//만약 권한이나 인증이 필요한 주소가 아니라면 이 필터를 타지않는다.
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager , UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository=userRepository;
    }

    //인증이나 권한이 필요한 주소요청이 있을때 해당 필터를 타게됨.
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("인증이나 권한이 필요한 주소 요청이 됨.");

        String jwtHeader = request.getHeader("Authorization");
        System.out.println("jwtHeader::"+jwtHeader);

       //header가 있는지 확인
        if(jwtHeader ==null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request,response);
            return;
        }

        //JWT 토큰을 검증을 해서 정상적인 사용자인지 확인을 한다

        String jwtToken = request.getHeader("Authorization").replace("Bearer","");
        System.out.println("jwtToken:"+jwtToken);
        String username = JWT.require(Algorithm.HMAC512("cos")).build().verify(jwtToken)
                .getClaim("username").asString();
        System.out.println("username:"+username);
        //서명이 정상적으로 됨
        if(username !=null){
            User user = userRepository.findByUsername(username);
            System.out.println("user:"+user);
            PrincipalDetails principalDetails = new PrincipalDetails(user);

           //JWT 토큰 서명을 통해서 서명이 정상이면 Authentication 객체를 만들어준다.
            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(principalDetails , null ,principalDetails.getAuthorities());

            // 강제로 시큐리티 세션에 접근하여 Authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            chain.doFilter(request,response);

        }

    }
}
