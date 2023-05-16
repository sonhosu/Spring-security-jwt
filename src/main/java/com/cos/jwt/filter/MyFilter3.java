package com.cos.jwt.filter;


import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("필터3 먼저 실행됨");

        //캐스팅 해주는 이유는 doFilter 로 넘어온 ServletRequest 보다 HttpServletRequest 가 HTTP의 데이터를 가져오는 기능이 더많음
        //ex) header 정보
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        // 토큰 : cos 이걸 만들어줘야 한다 . 그럼 언제 만들어주는가 ? id와 pw 가 정상적으로 들어와서 로그인이 완료되면 토큰을 만들고 응답해준다.
        // 그럼 클라이언트는 요청할때마다 header에 Authorization 에 value 값으로 토큰을 가져옴
        // 그때 토큰이 넘어오면 이토큰이 내가 발행한 토큰이 맞는지 검증하면됨.(RSA,HS256)
        if (req.getMethod().equals("POST")) {
            String headerAuth = req.getHeader("Authorization");
            System.out.println("headerAuth:" + headerAuth);

            if (headerAuth.equals("cos")) {

                chain.doFilter(req, res); //필터 체인을 다시 타도록 등록. 여기서 말하는 필터체인은 스프링 시큐리티 필터체인
            } else {
                PrintWriter out = res.getWriter();
                out.println("인증안됨");
            }
        }
    }
}