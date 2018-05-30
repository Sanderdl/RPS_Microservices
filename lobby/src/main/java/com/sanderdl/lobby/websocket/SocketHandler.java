package com.sanderdl.lobby.websocket;

import com.sanderdl.lobby.messaging.IGatewayObserver;
import com.sanderdl.lobby.messaging.MessageConsumer;
import com.sanderdl.lobby.messaging.Status;
import com.sanderdl.lobby.messaging.dto.MessageEvent;
import com.sanderdl.lobby.model.RoomEvent;
import com.sanderdl.lobby.service.RoomService;
import com.sanderdl.lobby.util.MessagingConverter;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
public class SocketHandler extends TextWebSocketHandler implements IGatewayObserver {

    private List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
    private Map<Long, WebSocketSession> users = new ConcurrentHashMap<>();

    private final MessageConsumer consumer = new MessageConsumer("match", "2", this);

    private final RoomService roomService;

    public SocketHandler() {
        this.roomService = new RoomService();
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        RoomEvent event = MessagingConverter.stringToClass(message.getPayload(), RoomEvent.class);
        users.putIfAbsent(event.getUserId(), session);

        String reply = roomService.handleRequest(event);

        for (WebSocketSession webSocketSession : sessions) {
            webSocketSession.sendMessage(new TextMessage(reply));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
    }

    @Override
    public void update(Object... param) {
        ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) param[0];
        MessageEvent event = MessagingConverter.stringToClass(record.value(), MessageEvent.class);

        if (event.getStatus() == Status.CREATED) {
            WebSocketSession session = users.get(event.getId());

            try {
                if (session != null)
                    session.sendMessage(new TextMessage(record.value()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
