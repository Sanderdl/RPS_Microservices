package com.sanderdl.dailyquest.service;

import com.sanderdl.dailyquest.domain.QuestProgress;
import com.sanderdl.dailyquest.domain.User;
import com.sanderdl.dailyquest.exception.ResourceNotFoundException;
import com.sanderdl.dailyquest.messaging.IGatewayObserver;
import com.sanderdl.dailyquest.messaging.MessageMatchConsumer;
import com.sanderdl.dailyquest.messaging.dto.MessageEvent;
import com.sanderdl.dailyquest.model.JwtUser;
import com.sanderdl.dailyquest.repository.UserRepository;
import com.sanderdl.dailyquest.util.MessagingConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class UserService implements IGatewayObserver, UserDetailsService{

    @Autowired
    private UserRepository userRepository ;

    @Autowired
    private QuestService questService;

    private MessageMatchConsumer consumer = new MessageMatchConsumer("users","dailyusers",this);

    private void createUser(User user){
        User u = userRepository.save(user);

        Set<QuestProgress> quests = new HashSet<>();
        quests.add(questService.getNewQuestForUser(u));
        quests.add(questService.getNewQuestForUser(u));
        quests.add(questService.getNewQuestForUser(u));
        user.setCurrentQuests(quests);

        u.setLastNewQuestGotten(new Timestamp(System.currentTimeMillis()));

        userRepository.save(u);
    }

    User getUserById (Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    void updateUser(User user){
        userRepository.save(user);
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
