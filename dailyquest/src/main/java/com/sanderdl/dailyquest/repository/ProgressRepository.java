package com.sanderdl.dailyquest.repository;

import com.sanderdl.dailyquest.domain.QuestProgress;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgressRepository extends JpaRepository<QuestProgress, Long> {
}
