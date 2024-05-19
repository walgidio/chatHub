package com.walgi.chatHub.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Document(collection = "messages")
@Getter
@Setter
public class Message {
    @Id
    private String id;
    private String sender;
    private String content;
    private LocalDateTime timestamp;
}
