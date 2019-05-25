package com.sanderdl.dailyquest.repository;

import com.sanderdl.dailyquest.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchUserRepository extends JpaRepository<User, Long> {
}
