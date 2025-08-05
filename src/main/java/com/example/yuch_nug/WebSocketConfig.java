package com.example.yuch_nug;

import com.example.yuch_nug.socket.OmokSocketHandler;
import com.example.yuch_nug.socket.ChessSocketHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private final OmokSocketHandler omokSocketHandler;
    private final ChessSocketHandler chessSocketHandler;

    public WebSocketConfig(OmokSocketHandler omokSocketHandler, ChessSocketHandler chessSocketHandler) {
        this.omokSocketHandler = omokSocketHandler;
        this.chessSocketHandler = chessSocketHandler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(omokSocketHandler, "/ws/omok").setAllowedOrigins("*");
        registry.addHandler(chessSocketHandler, "/ws/chess").setAllowedOrigins("*");
    }
}