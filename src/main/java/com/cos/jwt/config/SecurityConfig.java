package com.cos.jwt.config;

import com.cos.jwt.JwtAuthenticationFilter;
import com.cos.jwt.JwtAuthorizationFilter;
import com.cos.jwt.filter.MyFilter1;
//import com.cos.jwt.filter.MyFilter3;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextPersistenceFilter;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserRepository userRepository;
    @Autowired
    private final CorsFilter corsFilter;
    @Autowired
    private final CorsConfig corsConfig;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //스프링 시큐리티 필터가 실행되기전에 MyFilter1 가 실행되도록 등록. .addFilter() 이렇게 해서 바로 걸수없다.
        // http.addFilterBefore(new MyFilter3(), SecurityContextPersistenceFilter.class);
        http.csrf().disable();
         //세션을 사용하지 않겠다는 선언
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
              .and()
              .addFilter(corsFilter) //cross origin  정책을 벗어날수있다.
              .formLogin().disable()
              .httpBasic().disable() //기본인증방식 제거.
                .addFilter(new JwtAuthenticationFilter(authenticationManager())) //JwtAuthenticationFilter 필터가 동작하도록 추가.
                .addFilter(new JwtAuthorizationFilter(authenticationManager() , userRepository)) //JwtAuthorizationFilter 필터가 동작하도록 추가.
              .authorizeRequests()
              .antMatchers("/api/v1/user/**")
              .access("hasRole('ROLE_USER') or hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
              .antMatchers("/api/v1/manager/**")
              .access("hasRole('ROLE_MANAGER') or hasRole('ROLE_ADMIN')")
              .antMatchers("/api/v1/admin/**")
              .access(" hasRole('ROLE_ADMIN')")
              .anyRequest().permitAll();


    }
}
