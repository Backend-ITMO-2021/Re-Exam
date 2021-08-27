package com.dzeru.springchat.controllers;

import com.dzeru.springchat.entities.Message;
import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import com.dzeru.springchat.models.ChatMessage;
import com.dzeru.springchat.repos.MessageRepo;
import com.dzeru.springchat.repos.RoomRepo;
import com.dzeru.springchat.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ChatController {
    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);

    private SimpMessagingTemplate template;

    @Autowired
    public ChatController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    public RoomRepo roomRepo;

    @Autowired
    public MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    @MessageMapping("/chat/{roomId}")
//    @SendTo("/topic/public")
    public ChatMessage sendMessage(@DestinationVariable Long roomId, @Payload ChatMessage chatMessage) {
        this.template.convertAndSend("/topic/"+roomId, chatMessage);
        Message message = new Message();
        User user = (User) userService.loadUserByUsername(chatMessage.getSender());
        message.setSender(user);
        message.setRoom(roomRepo.getById(roomId));
        message.setContent(chatMessage.getContent());
        message.setTod(chatMessage.getTod());
        messageRepo.save(message);

        return chatMessage;
    }
    @MessageMapping("/chat/{roomId}/addUser")
//    @SendTo("/topic/public")
    public ChatMessage addUser(@DestinationVariable Long roomId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) throws Exception {
        logger.info("WS(me): User " + chatMessage.getSender() + " joined room: " + roomId);
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());

        this.template.convertAndSend("/topic/"+roomId, chatMessage);
        return chatMessage;
    }

    @MessageMapping("/chat/{roomId}/loadHistory")
    public List<ChatMessage> loadHistory(@DestinationVariable Long roomId) throws Exception {
        logger.info("WS(me): Loading history of room: " + roomId);
        Room room = roomRepo.getById(roomId);
        List<Message> messagesHistory = messageRepo.findAllByRoom(room);
        List<ChatMessage> chml = new ArrayList<>();
        for (Message msg: messagesHistory) {
            ChatMessage cMsg = new ChatMessage();
            cMsg.setSender(msg.getSender().getName());
            cMsg.setTod(msg.getTod());
            cMsg.setType(ChatMessage.MessageType.CHAT);
            cMsg.setContent(msg.getContent());
            chml.add(cMsg);
            this.template.convertAndSend("/topic/"+roomId, cMsg);
        }

        return chml;
    }
}
//User: " + username