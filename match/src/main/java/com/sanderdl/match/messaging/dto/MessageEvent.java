package com.sanderdl.match.messaging.dto;


import com.sanderdl.match.messaging.Status;

public class MessageEvent {
    private Long id;
    private String name;
    private Status status;

    public MessageEvent(Long id, String name, Status status) {
        this.id = id;
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
