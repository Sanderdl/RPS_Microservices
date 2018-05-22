package com.sanderdl.messaging.dto;

import com.sanderdl.messaging.MessageEvent;

/**
 * Created by sande on 5/21/2018.
 */
public class UserEvent {
    private Long userId;
    private MessageEvent messageEvent;

    public UserEvent(Long userId, MessageEvent messageEvent) {
        this.userId = userId;
        this.messageEvent = messageEvent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public MessageEvent getMessageEvent() {
        return messageEvent;
    }

    public void setMessageEvent(MessageEvent messageEvent) {
        this.messageEvent = messageEvent;
    }
}
