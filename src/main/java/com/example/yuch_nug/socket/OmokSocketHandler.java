package com.example.yuch_nug.socket;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;

@Component
public class OmokSocketHandler extends TextWebSocketHandler {

    private final Map<String, List<WebSocketSession>> rooms = new HashMap<>();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        System.out.println("🔌 연결됨: " + session.getId());
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        JsonNode node = mapper.readTree(message.getPayload());
        String type = node.get("type").asText();
        String roomId = node.get("room").asText();

        rooms.putIfAbsent(roomId, new ArrayList<>());
        List<WebSocketSession> roomSessions = rooms.get(roomId);

        if ("join".equals(type)) {
            roomSessions.add(session);
            String color = (roomSessions.size() == 1) ? "black" : "white";
            session.sendMessage(new TextMessage("{\"type\":\"start\",\"color\":\"" + color + "\"}"));
            // ✅ 상대방이 2명 되면: 서로에게 알림
            if (roomSessions.size() == 2) {
                for (WebSocketSession s : roomSessions) {
                    if (s.isOpen()) {
                        s.sendMessage(new TextMessage("{\"type\":\"opponent-joined\"}"));
                    }
                }
            }
        } else if ("move".equals(type)) {
            for (WebSocketSession s : roomSessions) {
                if (!s.getId().equals(session.getId()) && s.isOpen()) {
                    s.sendMessage(message); // 상대에게 전달
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        System.out.println("❌ 연결 종료: " + session.getId());
        rooms.values().forEach(list -> {
            if (list.remove(session)) {
                // 남은 상대에게 연결 종료 알림
                for (WebSocketSession s : list) {
                    if (s.isOpen()) {
                        try {
                            s.sendMessage(new TextMessage("{\"type\":\"opponent-left\"}"));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }
}