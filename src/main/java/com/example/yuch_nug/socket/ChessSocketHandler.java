package com.example.yuch_nug.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.*;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ChessSocketHandler extends TextWebSocketHandler {

    // 방ID -> [플레이어1, 플레이어2]
    private final Map<String, List<WebSocketSession>> rooms = new ConcurrentHashMap<>();
    private final Map<String, String> sessionRoomMap = new ConcurrentHashMap<>();
    private final Map<String, String> sessionColorMap = new ConcurrentHashMap<>();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        // 연결 성공 시점에는 아무 것도 하지 않음
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        Map<String, Object> payload = parseMessage(message.getPayload());
        String type = (String) payload.get("type");

        if ("join".equals(type)) {
            String room = (String) payload.get("room");
            sessionRoomMap.put(session.getId(), room);

            rooms.putIfAbsent(room, new ArrayList<>());
            List<WebSocketSession> players = rooms.get(room);

            if (players.size() < 2) {
                players.add(session);
                String color = players.size() == 1 ? "white" : "black";
                sessionColorMap.put(session.getId(), color);

                session.sendMessage(new TextMessage("{" +
                        "\"type\":\"start\"," +
                        "\"color\":\"" + color + "\"}"));

                // 상대가 들어오면 알림
                if (players.size() == 2) {
                    for (WebSocketSession player : players) {
                        player.sendMessage(new TextMessage("{\"type\":\"opponent-joined\"}"));
                    }
                }
            }
        } else if ("move".equals(type)) {
            String room = (String) payload.get("room");
            List<WebSocketSession> players = rooms.get(room);
            if (players == null) return;

            for (WebSocketSession player : players) {
                if (!player.getId().equals(session.getId())) {
                    player.sendMessage(new TextMessage(message.getPayload()));
                }
            }
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String room = sessionRoomMap.get(session.getId());
        if (room != null) {
            List<WebSocketSession> players = rooms.get(room);
            if (players != null) {
                players.remove(session);
                for (WebSocketSession player : players) {
                    player.sendMessage(new TextMessage("{\"type\":\"opponent-left\"}"));
                }
                if (players.isEmpty()) {
                    rooms.remove(room);
                }
            }
        }
        sessionRoomMap.remove(session.getId());
        sessionColorMap.remove(session.getId());
    }

    private Map<String, Object> parseMessage(String json) {
        Map<String, Object> map = new HashMap<>();
        json = json.trim().replaceAll("[{}\"]", "");
        String[] pairs = json.split(",");
        for (String pair : pairs) {
            String[] kv = pair.split(":");
            if (kv.length == 2) {
                map.put(kv[0].trim(), kv[1].trim());
            }
        }
        return map;
    }
}