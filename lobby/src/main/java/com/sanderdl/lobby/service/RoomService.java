package com.sanderdl.lobby.service;

import com.sanderdl.lobby.messaging.MessageProducer;
import com.sanderdl.lobby.messaging.Status;
import com.sanderdl.lobby.messaging.dto.MessageEvent;
import com.sanderdl.lobby.model.Room;
import com.sanderdl.lobby.model.RoomEvent;
import com.sanderdl.lobby.util.MessagingConverter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoomService {
    private static final Map<String, Room> rooms = new HashMap<>();

    private final MessageProducer producer = new MessageProducer();

    public RoomService() {

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


            sendToMatch(room.getPlayer1(), event.getName());
            sendToMatch(room.getPlayer2(), event.getName());
        }

        rooms.put(room.getName(), room);

        return eventToJson(event);
    }

    private String handleLeave(RoomEvent event) {
        Room room = rooms.get(event.getName());

        if (room.getPlayer1() != null && room.getPlayer1().equals(event.getUserId())) {
            room.setPlayer1(null);
            room.setSlots(room.getSlots() - 1);
        }

        if (room.getPlayer2() != null && room.getPlayer2().equals(event.getUserId())) {
            room.setPlayer2(null);
            room.setSlots(room.getSlots() - 1);
        }

        rooms.put(room.getName(), room);

        return eventToJson(event);
    }

    private String eventToJson(RoomEvent event) {
        return MessagingConverter.classToString(rooms.get(event.getName()));
    }

    public List<Room> getRooms() {
        return new ArrayList<>(rooms.values());
    }

    private void sendToMatch(Long userId, String roomName){
        MessageEvent event = new MessageEvent(userId, roomName, Status.CREATED);
        String message = MessagingConverter.classToString(event);

        producer.sendMessage(message, "match");
    }

    public String removePlayerFromRoom(Long userId, String roomName){
        Room room = rooms.get(roomName);

        if (userId.equals(room.getPlayer1()))
            room.setPlayer1(null);

        if (userId.equals(room.getPlayer2()))
            room.setPlayer2(null);

        rooms.put(roomName, room);

        return MessagingConverter.classToString(room);
    }
}