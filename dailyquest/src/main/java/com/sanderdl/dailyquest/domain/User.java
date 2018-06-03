package com.sanderdl.dailyquest.domain;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {
    @Id
    private Long userId;

    private String username;

    private Timestamp lastNewQuestGotten;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private Set<QuestProgress> currentQuests = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Timestamp getLastNewQuestGotten() {
        return lastNewQuestGotten;
    }

    public void setLastNewQuestGotten(Timestamp lastNewQuestGotten) {
        this.lastNewQuestGotten = lastNewQuestGotten;
    }

    public Set<QuestProgress> getCurrentQuests() {
        return currentQuests;
    }

    public void setCurrentQuests(Set<QuestProgress> currentQuests) {
        this.currentQuests = currentQuests;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
