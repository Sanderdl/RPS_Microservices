package com.sanderdl.dailyquest.repository;

import com.sanderdl.dailyquest.domain.Quest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface QuestRepository extends JpaRepository<Quest, Long> {
    @Query(value = "SELECT * FROM quests q " +
            "INNER JOIN quest_progress qp ON q.id = qp.quest_id " +
            "WHERE qp.user_id = :id", nativeQuery = true)
    Set<Quest> getQuestsForUser(@Param("id") Long id);
}
