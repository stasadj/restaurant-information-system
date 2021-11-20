package com.restaurant.backend.controller;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
@AllArgsConstructor
public class WebSocketController {
    private final SimpMessageSendingOperations messagingTemplate;

    @MessageMapping("/message")
    @SendToUser("/queue/reply")
    public String processMessageFromClient(@Payload String message, Principal principal) throws Exception {
        //messagingTemplate.convertAndSendToUser(principal.getName(), "/queue/reply", name);
        return new Gson().fromJson(message, Map.class).get("name").toString();
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }
}
