package com.chat.repository;

import com.chat.domain.ChatMessage;
import com.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;

/**
 * @author
 */
public interface MessageChatRepository extends CrudRepository<ChatMessage, Long> {
}
