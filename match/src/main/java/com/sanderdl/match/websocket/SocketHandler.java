package com.sanderdl.match.websocket;


import com.sanderdl.match.model.Match;
import com.sanderdl.match.model.MatchRequest;
import com.sanderdl.match.model.canSee;
import com.sanderdl.match.service.MatchService;
import com.sanderdl.match.util.MessagingConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SocketHandler extends TextWebSocketHandler {

    private Map<String, Long > users = new ConcurrentHashMap<>();
    private Map<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();

    private MatchService matchService = new MatchService();


    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        MatchRequest request = MessagingConverter.stringToClass(message.getPayload(), MatchRequest.class);
        sessions.put(request.getUserId(), session);

        Match match = matchService.handleRequest(request);

        String matchJson = MessagingConverter.classToString(match);

        if (match.getCanSee() == canSee.ALL){
            WebSocketSession session1 = sessions.get(match.getPlayer1());
            WebSocketSession session2 = sessions.get(match.getPlayer2());

            session1.sendMessage(new TextMessage(matchJson));
            session2.sendMessage(new TextMessage(matchJson));
        }else if (match.getCanSee() == canSee.SELF)
            session.sendMessage(new TextMessage(matchJson));

    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        users.putIfAbsent(session.getId(), 0L);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status){
        Long id = users.get(session.getId());
        sessions.remove(id);
        users.remove(session.getId());

    }

}
