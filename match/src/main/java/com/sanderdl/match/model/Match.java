package com.sanderdl.match.model;

import java.util.HashMap;
import java.util.Map;

public class Match {
    private Long player1;
    private Long player2;

    private Map<Integer,Answer> answersPlayer1;
    private Map<Integer,Answer> answersPlayer2;

    private int winsPlayer1;
    private int winsPlayer2;

    private int currentRound;

    private transient canSee canSee;

    public Match() {
        answersPlayer1 = new HashMap<>();
        answersPlayer2 = new HashMap<>();
    }

    public Long getPlayer1() {
        return player1;
    }

    public void setPlayer1(Long player1) {
        this.player1 = player1;
    }

    public Long getPlayer2() {
        return player2;
    }

    public void setPlayer2(Long player2) {
        this.player2 = player2;
    }

    public Map<Integer, Answer> getAnswersPlayer1() {
        return answersPlayer1;
    }

    public void setAnswersPlayer1(Map<Integer, Answer> answersPlayer1) {
        this.answersPlayer1 = answersPlayer1;
    }

    public Map<Integer, Answer> getAnswersPlayer2() {
        return answersPlayer2;
    }

    public void setAnswersPlayer2(Map<Integer, Answer> answerPlayer2) {
        this.answersPlayer2 = answerPlayer2;
    }

    public int getWinsPlayer1() {
        return winsPlayer1;
    }

    public void setWinsPlayer1(int winsPlayer1) {
        this.winsPlayer1 = winsPlayer1;
    }

    public int getWinsPlayer2() {
        return winsPlayer2;
    }

    public void setWinsPlayer2(int winsPlayer2) {
        this.winsPlayer2 = winsPlayer2;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public com.sanderdl.match.model.canSee getCanSee() {
        return canSee;
    }

    public void setCanSee(com.sanderdl.match.model.canSee canSee) {
        this.canSee = canSee;
    }
}
