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

    private static final String MATCH_TOPIC = "match";

    public Match handleRequest(MatchRequest request) {
        Match match = matches.get(request.getRoomName());

        if (match != null && match.getCurrentRound() > 0) {
            match = handleRound(match, request);
        }

        match = handleStartMatch(match, request);


        matches.put(request.getRoomName(), match);

        if (match.getCurrentRound() > 3) {
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

        if (roundEnded(match)) {
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

    private void endMatch(Match match, MatchRequest request) {
        Long winner = getWinner(match);
        int finalAwnswer = getFinalAnswer(match, winner);

                MessageEvent event = new MessageEvent(winner, String.valueOf(finalAwnswer), Status.UPDATED);
        String message = MessagingConverter.classToString(event);
        producer.sendMessage(message, MATCH_TOPIC);

        MessageEvent player1 = new MessageEvent(match.getPlayer1(), request.getRoomName(), Status.DELETED);
        MessageEvent player2 = new MessageEvent(match.getPlayer2(), request.getRoomName(), Status.DELETED);

        String deleted1 = MessagingConverter.classToString(player1);
        String deleted2 = MessagingConverter.classToString(player2);

        producer.sendMessage(deleted1, MATCH_TOPIC);
        producer.sendMessage(deleted2, MATCH_TOPIC);

        matches.put(request.getRoomName(), null);
    }

    private Long getWinner(Match match) {
        if (match.getWinsPlayer1() > match.getWinsPlayer2())
            return match.getPlayer1();

        else if (match.getWinsPlayer2() > match.getWinsPlayer1())
            return match.getPlayer2();

        return 0L;
    }

    private int getFinalAnswer(Match match, Long winner) {
        if (winner.equals(match.getPlayer1()))
            return match.getAnswersPlayer1().get(3).getType();

        if (winner.equals(match.getPlayer2()))
            return match.getAnswersPlayer2().get(3).getType();

        return 0;
    }
}
