package com.cos.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter(){

        UrlBasedCorsConfigurationSource source= new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // 내서버가 응답할때 json 을 자바스크립트에서 처리할수있게 할지 설정하는것
        config.addAllowedOrigin("*");  //모든 ip에 응답을 허용하겠다.
        config.addAllowedHeader("*");  //모든 header에 응답을 허용하겠다
        config.addAllowedMethod("*");  //모든 method에 대해 허용하겠다.
        source.registerCorsConfiguration("/api/**" , config);

        return new CorsFilter(source);

    }
}