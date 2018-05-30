package com.sanderdl.lobby.controller;

import com.sanderdl.lobby.model.Room;
import com.sanderdl.lobby.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public Map<String, Room> getRooms(){
        return roomService.getRooms();
    }
}
