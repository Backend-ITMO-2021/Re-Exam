package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

public interface MessagesTopRepository extends JpaRepository<Message, Long> {
    // TODO: test query
    @Query("SELECT new com.sb.ifmo.reexam.data.MessagesTop(user.username, COUNT(user.username)) FROM Message message JOIN message.room room JOIN message.user user WHERE room = ?1 GROUP BY user ORDER BY COUNT(user.username) DESC")
    Set<MessagesTop> findTop10ByRoomGroupByUserOrderByCountDesc(Room room);
}
