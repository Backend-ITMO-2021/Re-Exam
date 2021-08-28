package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.*;
import com.sb.ifmo.reexam.requests.CreateMessageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/messages", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @PostMapping("/create")
    public String room(@AuthenticationPrincipal OAuth2User principal, @RequestBody CreateMessageRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(requestBody.getRoomId());
        Message newMessage = new Message(requestBody.getText(), room, userByPrincipal);
        messageRepository.save(newMessage);

        return "{\"message\": \"Message was successfully posted\"}";
    }
}
