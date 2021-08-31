package com.chatty.websocket;

import com.chatty.model.*;
import com.chatty.service.RoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class ChatController {
    private RoomService roomService;

    // @MessageMapping("/rooms/{roomId}/sendMessage")
    // public void renderRoom(@DestinationVariable int roomId, Message message) {

    // }

    @MessageMapping("/room/sendMessage")
    @SendTo("/app/message")
    public Message sendMessage(String message) {
        System.out.println(message);
        return new Message(message);
    }

//    @SubscribeMapping("/allRooms")
//    public Set<Room> renderAllRooms() {
//        return roomService.getRoomList();
//    }
}
