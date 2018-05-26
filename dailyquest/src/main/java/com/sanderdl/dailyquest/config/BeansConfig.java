package com.sanderdl.dailyquest.config;


import com.sanderdl.dailyquest.service.UserService;
import com.sanderdl.dailyquest.util.JwtTokenUtil;
import net.sourceforge.jeval.Evaluator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.sanderdl.dailyquest.service.QuestService;

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
    public QuestService questService(){
        return new QuestService();
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public Evaluator evaluator() { return new Evaluator();}


}
