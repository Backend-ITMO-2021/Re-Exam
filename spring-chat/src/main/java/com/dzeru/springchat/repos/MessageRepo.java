package com.dzeru.springchat.repos;

import com.dzeru.springchat.entities.Message;
import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@Repository
public interface MessageRepo extends JpaRepository<Message, Long>
{
    List<Message> findBySender(User user);
    List<Message> findAllByRoom(Room room);
    List<Message> findAllByRoomAndSender(Room room, User user);
    List<Message> findAllByRoomAndTodAfterAndTodBefore(Room room, Timestamp after, Timestamp before);
}
