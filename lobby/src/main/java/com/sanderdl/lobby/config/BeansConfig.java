package com.sanderdl.lobby.config;


import com.sanderdl.lobby.service.RoomService;
import com.sanderdl.lobby.service.UserService;
import com.sanderdl.lobby.util.JwtTokenUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
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
    @Scope("singleton")
    public RoomService roomService() { return new RoomService();}



}
