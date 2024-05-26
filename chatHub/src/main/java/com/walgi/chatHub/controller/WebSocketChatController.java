package com.walgi.chatHub.controller;

import com.walgi.chatHub.model.Message;
import com.walgi.chatHub.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;

@Controller
public class WebSocketChatController {
    @Autowired
    private MessageRepository messageRepository;

    @MessageMapping("/send")
    @SendTo("/topic/messages") // Enviar a mensagem para todos os inscritos no tópico
    public Message send(Message message) throws Exception {
        message.setTimestamp(LocalDateTime.now());
        messageRepository.save(message);
        return message;
    }
}
