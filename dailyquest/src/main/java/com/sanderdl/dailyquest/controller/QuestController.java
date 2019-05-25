package com.sanderdl.dailyquest.controller;

import com.sanderdl.dailyquest.domain.Quest;
import com.sanderdl.dailyquest.service.QuestService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@RequestMapping("quests")
public class QuestController {

    private QuestService questService;

    public QuestController(QuestService questService){
        this.questService = questService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Quest create (@RequestBody Quest quest){
        return questService.createQuest(quest);
    }

    @GetMapping("user/{id}")
    public Set<Quest> getQuestsForUser(@PathVariable("id") Long id){
        return questService.getQuestsForUser(id);
    }


}
