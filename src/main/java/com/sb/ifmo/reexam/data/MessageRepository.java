package com.sb.ifmo.reexam.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findAllByRoomAndUser(Room room, CustomUser user);
    List<Message> findAllByRoomAndTimeBetween(Room room, Date timeStart, Date timeEnd);
}
