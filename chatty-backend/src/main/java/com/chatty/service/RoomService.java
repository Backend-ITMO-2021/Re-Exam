package com.chatty.service;

import com.chatty.model.Room;
import com.chatty.model.User;
import com.chatty.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

//    public Set<Room> getRoomList() {
//        return roomRepository.getAllRoomNames();
//    }

}
