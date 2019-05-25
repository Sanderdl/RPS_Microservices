package com.sanderdl.lobby.service;


import com.sanderdl.lobby.domain.User;
import com.sanderdl.lobby.exception.ResourceNotFoundException;
import com.sanderdl.lobby.messaging.IGatewayObserver;
import com.sanderdl.lobby.messaging.MessageConsumer;
import com.sanderdl.lobby.messaging.dto.MessageEvent;
import com.sanderdl.lobby.model.JwtUser;
import com.sanderdl.lobby.repository.UserRepository;
import com.sanderdl.lobby.util.MessagingConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.LinkedList;
import java.util.List;

public class UserService implements IGatewayObserver, UserDetailsService{

    private final MessageConsumer consumer = new MessageConsumer("users","lobby","1",this);

    @Autowired
    private UserRepository userRepository ;

    private void createUser(User user){

        userRepository.save(user);
    }

    public User getUserById (Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }


    @Override
    public void update(Object... param) {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) param[0];
        MessageEvent event = MessagingConverter.stringToClass(record.value(), MessageEvent.class);
        User user = new User();
        user.setUserId(event.getId());
        user.setUsername(event.getName());

        createUser(user);
    }

    @Override
    public UserDetails loadUserByUsername(String s) {
        User user = userRepository.findByUsername(s);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", s));
        }else {

            List<GrantedAuthority> authorities = new LinkedList<>();

            return new JwtUser(
                    user.getUsername(),
                    user.getUsername(),
                    authorities
            );
        }
    }
}
