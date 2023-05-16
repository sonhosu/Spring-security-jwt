package com.cos.jwt.config;


import com.cos.jwt.filter.MyFilter1;
import com.cos.jwt.filter.MyFilter2;
//import com.cos.jwt.filter.MyFilter3;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.FilterRegistration;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean<MyFilter1> filter1(){
        FilterRegistrationBean<MyFilter1> bean = new FilterRegistrationBean<>(new MyFilter1());
        bean.addUrlPatterns("/*");  //모든 url에 대해서 필터를 건다.
        bean.setOrder(0);  //우선순위를 1순위로 설정
        return bean;
    }

    @Bean
    public FilterRegistrationBean<MyFilter2> filter2(){
        FilterRegistrationBean<MyFilter2> bean = new FilterRegistrationBean<>(new MyFilter2());
        bean.addUrlPatterns("/*");  //모든 url에 대해서 필터를 건다.
        bean.setOrder(1);  //우선순위를 2순위로 설정
        return bean;
    }

   /* @Bean
    public FilterRegistrationBean<MyFilter3> filter3(){
        FilterRegistrationBean<MyFilter3> bean = new FilterRegistrationBean<>(new MyFilter3());
        bean.addUrlPatterns("/*");  //모든 url에 대해서 필터를 건다.
        bean.setOrder(2);  //우선순위를 3순위로 설정
        return bean;
    }*/
}
