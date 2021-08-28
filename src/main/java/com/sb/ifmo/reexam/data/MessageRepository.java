package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Set<Message> findAllByRoom(Room room);
}
