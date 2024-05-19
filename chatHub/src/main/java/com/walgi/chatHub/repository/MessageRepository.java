package com.walgi.chatHub.repository;

import com.walgi.chatHub.model.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MessageRepository extends MongoRepository<Message, String> {
}
