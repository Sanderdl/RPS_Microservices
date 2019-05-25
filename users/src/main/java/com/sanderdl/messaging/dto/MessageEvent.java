package com.sanderdl.messaging.dto;

import com.sanderdl.messaging.Status;

public class MessageEvent {
    private Long id;
    private String name;
    private Status status;

    public MessageEvent(Long userId, String name, Status status) {
        this.id = userId;
        this.name = name;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long userId) {
        this.id = userId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
