package com.sanderdl.dailyquest.service;

import com.sanderdl.dailyquest.domain.User;
import com.sanderdl.dailyquest.repository.MatchUserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class MatchUserService {
    @Autowired
    private MatchUserRepository matchUserRepository;

    User getUserById (Long id) {
        return matchUserRepository.findById(id)
                .orElse(null);
    }

    User save(User user){
        return matchUserRepository.save(user);
    }
}
