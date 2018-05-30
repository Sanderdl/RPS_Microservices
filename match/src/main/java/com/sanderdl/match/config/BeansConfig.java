package com.sanderdl.match.config;


import com.sanderdl.match.service.MatchService;
import com.sanderdl.match.service.UserService;
import com.sanderdl.match.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Configuration
public class BeansConfig {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil(){
        return new JwtTokenUtil();
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public MatchService matchService() {return new MatchService();}





}
