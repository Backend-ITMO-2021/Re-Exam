package com.chatty.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.chatty.model.Message;
@Repository
public interface UserRoomRepository extends JpaRepository<Message, Long> {
}
