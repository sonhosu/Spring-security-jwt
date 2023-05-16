package com.cos.jwt.auth;

import com.cos.jwt.model.User;
import com.cos.jwt.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// http://localhost:8080/login => 여기서 동작을 하지않는다 .
// 이유는 SecurityConfig 파일에 .formLogin().disable()을 선언했기 때문
// 그래서 UserDetailsService 의 loadUserByUsername 메서드가 동작하게 하기위해서 새로운 필터를 추가해줘야 한다 .
@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private  final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("PrincipalDetailsService 의 loadUserByUsername ");
        User user = userRepository.findByUsername(username);
        return new PrincipalDetails(user);
    }
}
