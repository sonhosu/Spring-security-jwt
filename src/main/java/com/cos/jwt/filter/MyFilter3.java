package com.cos.jwt.filter;


import javax.servlet.*;
import java.io.IOException;

public class MyFilter3 implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("필터3");

        chain.doFilter(request,response); //필터 체인을 다시 타도록 등록. 여기서 말하는 필터체인은 스프링 시큐리티 필터체인
    }
}
