package com.sanderdl.match.service;

import com.sanderdl.match.messaging.MessageProducer;
import com.sanderdl.match.messaging.Status;
import com.sanderdl.match.messaging.dto.MessageEvent;
import com.sanderdl.match.model.Answer;
import com.sanderdl.match.model.Match;
import com.sanderdl.match.model.MatchRequest;
import com.sanderdl.match.model.canSee;
import com.sanderdl.match.util.MessagingConverter;

import java.util.HashMap;
import java.util.Map;

public class MatchService {

    private final Map<String, Match> matches = new HashMap<>();

    private final MessageProducer producer = new MessageProducer();

    public Match handleRequest(MatchRequest request) {
        Match match = matches.get(request.getRoomName());

        if (match != null && match.getCurrentRound() > 0) {
            match = handleRound(match, request);
        }

        match = handleStartMatch(match, request);


        matches.put(request.getRoomName(), match);

        if(match.getCurrentRound() > 3){
            endMatch(match, request);
        }

        return match;
    }

    private Match handleStartMatch(Match match, MatchRequest request) {
        if (match == null) {
            match = new Match();
            match.setPlayer1(request.getUserId());
            match.setCanSee(canSee.SELF);

        } else if (match.getPlayer2() == null) {
            match.setPlayer2(request.getUserId());
            match.setCurrentRound(match.getCurrentRound() + 1);
            match.setCanSee(canSee.ALL);
        }


        return match;
    }

    private Match handleRound(Match match, MatchRequest request) {

        int round = match.getCurrentRound();

        if (roundEnded(match)) {
            match.setCurrentRound(match.getCurrentRound() + 1);

            int winner = roundWinner(
                    match.getAnswersPlayer1().get(round),
                    match.getAnswersPlayer2().get(round)
            );

            if (winner == 1)
                match.setWinsPlayer1(match.getWinsPlayer1() + 1);
            else if (winner == 2) {
                match.setWinsPlayer2(match.getWinsPlayer2() + 1);
            }

            match.setCanSee(canSee.ALL);

            return match;
        }

        if (request.getUserId().equals(match.getPlayer1())) {
            Answer answer = new Answer();
            answer.setType(request.getType());
            match.getAnswersPlayer1().put(round, answer);
        } else if (request.getUserId().equals(match.getPlayer2())) {
            Answer answer = new Answer();
            answer.setType(request.getType());
            match.getAnswersPlayer2().put(round, answer);
        }

        match.setCanSee(canSee.SELF);

        if (roundEnded(match)){
            handleRound(match, request);
        }



        return match;
    }

    private int roundWinner(Answer player1, Answer player2) {
        return player1.winFrom(player2.getType());
    }

    private boolean roundEnded(Match match) {
        int round = match.getCurrentRound();

        return (match.getAnswersPlayer1().get(round) != null && match.getAnswersPlayer2().get(round) != null);

    }

    private void endMatch(Match match, MatchRequest request){
        Long winner = match.getWinsPlayer1() >= match.getWinsPlayer2() ? match.getPlayer1() : match.getPlayer2();

        MessageEvent event = new MessageEvent(winner, String.valueOf(request.getType()), Status.UPDATED);
        String message = MessagingConverter.classToString(event);
        producer.sendMessage(message, "match");

        matches.put(request.getRoomName(), null);
    }
}
