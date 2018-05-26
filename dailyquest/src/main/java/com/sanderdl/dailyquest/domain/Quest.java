package com.sanderdl.dailyquest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "quests")
@JsonIgnoreProperties(value = {"userProgress"}, allowSetters = true)
public class Quest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String evaluation;

    @OneToMany(mappedBy = "quest")
    private Set<QuestProgress> userProgress = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<QuestProgress> getUserProgress() {
        return userProgress;
    }

    public void setUserProgress(Set<QuestProgress> userProgress) {
        this.userProgress = userProgress;
    }

    public String getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(String evaluation) {
        this.evaluation = evaluation;
    }
}
