package com.sanderdl.configuration;


import com.sanderdl.messaging.UserAppGateway;
import com.sanderdl.service.UserService;
import com.sanderdl.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class BeansConfig {

    @Bean
    public UserService userService(){
        return new UserService(bCryptPasswordEncoder());
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    @Bean
    public UserAppGateway userAppGateway(){
        return new UserAppGateway();
    }
}
