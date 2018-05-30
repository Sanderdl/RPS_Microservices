package com.sanderdl.lobby.service;

import com.sanderdl.lobby.messaging.MessageProducer;
import com.sanderdl.lobby.messaging.Status;
import com.sanderdl.lobby.messaging.dto.MessageEvent;
import com.sanderdl.lobby.model.Room;
import com.sanderdl.lobby.model.RoomEvent;
import com.sanderdl.lobby.util.MessagingConverter;


import java.util.HashMap;
import java.util.Map;

public class RoomService {
    private final Map<String, Room> rooms;

    private final MessageProducer producer = new MessageProducer();

    public RoomService() {
        rooms = new HashMap<>();
        rooms.put("Room 1", new Room("Room 1"));
        rooms.put("Room 2", new Room("Room 2"));
        rooms.put("Room 3", new Room("Room 3"));
        rooms.put("Room 4", new Room("Room 4"));
        rooms.put("Room 5", new Room("Room 5"));
    }

    public String handleRequest(RoomEvent event) {
        switch (event.getIntention()) {
            case ENTER:
                return handleEnter(event);
            case LEAVE:
                return handleLeave(event);
        }
        return eventToJson(event);
    }

    private String handleEnter(RoomEvent event) {

        Room room = rooms.get(event.getName());

        if (room.getPlayer1() == null) {
            room.setPlayer1(event.getUserId());
            room.setSlots(room.getSlots() + 1);
        } else if (room.getPlayer2() == null) {
            room.setPlayer2(event.getUserId());
            room.setSlots(room.getSlots() + 1);


            sendToMatch(room.getPlayer1());
            sendToMatch(room.getPlayer2());
        }

        rooms.put(room.getName(), room);

        return eventToJson(event);
    }

    private String handleLeave(RoomEvent event) {
        Room room = rooms.get(event.getName());

        if (room.getPlayer1().equals(event.getUserId())) {
            room.setPlayer1(null);
            room.setSlots(room.getSlots() - 1);
        }

        if (room.getPlayer2().equals(event.getUserId())) {
            room.setPlayer2(null);
            room.setSlots(room.getSlots() - 1);
        }

        rooms.put(room.getName(), room);

        return eventToJson(event);
    }

    private String eventToJson(RoomEvent event) {
        return MessagingConverter.classToString(rooms.get(event.getName()));
    }

    public Map<String, Room> getRooms() {
        return this.rooms;
    }

    private void sendToMatch(Long userId){
        MessageEvent event = new MessageEvent(userId, null, Status.CREATED);
        String message = MessagingConverter.classToString(event);

        producer.sendMessage(message, "match");
    }
}
