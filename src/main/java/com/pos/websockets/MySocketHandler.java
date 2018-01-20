package com.pos.websockets;

import com.google.gson.Gson;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by rrampall on 20/01/18.
 */
@Component
public class MySocketHandler extends TextWebSocketHandler {


    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        for(WebSocketSession webSocketSession : sessions) {
            Map value = new Gson().fromJson(message.getPayload(), Map.class);
            try{
                webSocketSession.sendMessage(new TextMessage("name = " + value.get("name") + "  ||  " + "value = " + value.get("value")));

            }catch (Exception e) {
                System.out.println("###################################");
                System.out.println(e);

            }
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
        System.out.println("###################################");
        System.out.println(session);

    }
}
