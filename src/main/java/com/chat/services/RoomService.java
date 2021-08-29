package com.chat.services;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.chat.domain.Message;
import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author
 */
@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserDetailsRepository userDetailsRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    public Room getRoomById(String roomName) {
        return roomRepository.findById(Long.valueOf(roomName)).orElseThrow(IllegalStateException::new);
    }

    public User getRoomAdmin(Room room) {
        return room.getUserAdmin();
    }

    public List<User> getUsers(Room room) {
        return room.getUsers();
    }

    public void addUser(Room room, User user) {
        room.getUsers().add(user);
        user.getRooms().add(room);

        roomRepository.save(room);
        userDetailsRepository.save(user);
    }

    public void changeRoomName(String roomId, String name) {
        Room room = getRoomById(roomId);
        room.setTitle(name);
        roomRepository.save(room);
    }

    public void addMessage(Room currentRoom, User user, String text) {
        Date date = new Date();
        Message message = new Message(text, user, currentRoom, date);
        messageRepository.save(message);
    }

    public boolean isActionByAdmin(Room room) {
        User user = userService.getCurrentUser();
        return room.getUserAdmin().getId().equals(user.getId());
    }

    public boolean isActionByMemberOfChat(Room currentRoom) {
        return currentRoom.getUsers().stream().map(User::getId).collect(Collectors.toList())
                .contains(userService.getCurrentUser().getId());
    }
}
