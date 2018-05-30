package com.sanderdl.dailyquest.service;

import com.sanderdl.dailyquest.domain.QuestProgress;
import com.sanderdl.dailyquest.domain.User;
import com.sanderdl.dailyquest.messaging.IGatewayObserver;
import com.sanderdl.dailyquest.messaging.MessageMatchConsumer;
import com.sanderdl.dailyquest.messaging.Status;
import com.sanderdl.dailyquest.messaging.dto.MessageEvent;
import com.sanderdl.dailyquest.util.MessagingConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class MatchService implements IGatewayObserver {

    @Autowired
    private QuestService questService;

    @Autowired
    private UserService userService;

    private MessageMatchConsumer messageConsumer = new MessageMatchConsumer("match", "2", this);

    @Override
    public void update(Object... param) {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) param[0];
        MessageEvent event = MessagingConverter.stringToClass(record.value(), MessageEvent.class);

        if (event.getStatus() == Status.UPDATED){
           User u = userService.getUserById(event.getId());

           for (QuestProgress qp: u.getCurrentQuests()){
               qp.setType(Integer.valueOf(event.getName()));
               qp.setProgress(1);
           }

            checkIfQuestsComplete(event.getId());
        }
    }

    private void checkIfQuestsComplete(Long userId) {
        User user = userService.getUserById(userId);

        Set<QuestProgress> quests = user.getCurrentQuests();

        for (QuestProgress qp : quests) {
            boolean complete = questService.questComplete(qp);

            if (complete) {
                questService.deleteQuestProgress(qp);
            }
        }
    }
}
