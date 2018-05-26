package com.sanderdl.dailyquest.service;

import com.sanderdl.dailyquest.domain.Quest;
import com.sanderdl.dailyquest.domain.QuestProgress;
import com.sanderdl.dailyquest.domain.User;
import com.sanderdl.dailyquest.repository.ProgressRepository;
import com.sanderdl.dailyquest.repository.QuestRepository;
import net.sourceforge.jeval.EvaluationConstants;
import net.sourceforge.jeval.EvaluationException;
import net.sourceforge.jeval.Evaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

public class QuestService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ProgressRepository progressRepository;

    @Autowired
    private UserService userService;

    @Autowired
    Evaluator evaluator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final Random random = new Random();

    private static final long MILLIS_PER_DAY = 24 * 60 * 60 * 1000L;

    public Quest createQuest(Quest quest) {
        return questRepository.save(quest);
    }

    public Set<Quest> getQuestsForUser(Long id) {
        User user = userService.getUserById(id);

        if (user != null && userCanHaveNewQuest(user)) {
            user.getCurrentQuests().add(
                    getNewQuestForUser(user)
            );
            userService.updateUser(user);
        }

        return questRepository.getQuestsForUser(id);
    }

    private boolean userCanHaveNewQuest(User user) {

        if (user.getCurrentQuests().size() > 2)
            return false;

        Timestamp now = new Timestamp(System.currentTimeMillis());
        Timestamp date = user.getLastNewQuestGotten();

        return (date.getTime() - now.getTime()) >= MILLIS_PER_DAY;
    }

    QuestProgress getNewQuestForUser(User user) {
        int i = random.nextInt(3)+ 1;

        Quest quest = null;

        Optional<Quest> optional = questRepository.findById((long)i);
        if (optional.isPresent())
            quest = optional.get();

        QuestProgress progress = new QuestProgress();
        progress.setQuest(quest);
        progress.setUser(user);

        return progressRepository.save(progress);
    }

    private boolean questComplete(QuestProgress progress){
        String expression = progress.getQuest().getEvaluation();

        evaluator.putVariable("type", progress.getType());
        evaluator.putVariable("progress",String.valueOf(progress.getProgress()));

        try {
            String match = evaluator.evaluate(expression);
            return (match.equals(EvaluationConstants.BOOLEAN_STRING_TRUE));

        } catch (EvaluationException e) {
            logger.error(e.getMessage(), e);
        }

        return false;
    }

    public void deleteQuestProgress(QuestProgress questProgress){
        questProgress.setUser(null);
        questProgress.setQuest(null);
        progressRepository.delete(questProgress);
    }


}
