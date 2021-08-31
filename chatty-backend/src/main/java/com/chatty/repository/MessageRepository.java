package com.chatty.repository;

import com.chatty.model.Message;
import com.chatty.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MessageRepository extends JpaRepository<Message, String> {
    Optional<Message> findAllBySenderId(User user);
}
