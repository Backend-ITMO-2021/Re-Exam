package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.*;
import com.sb.ifmo.reexam.requests.AllMessagesRequest;
import com.sb.ifmo.reexam.requests.ChangeRoomNameRequest;
import com.sb.ifmo.reexam.requests.InviteRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/{room_id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessagesTopRepository messagesTopRepository;

    // tested
    @GetMapping
    public String room(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.isAvailabe(userByPrincipal)) {
            if (!room.getUsers().contains(userByPrincipal)) {
                room.addUser(userByPrincipal);
                roomRepository.save(room);
            }
            JSONObject response = new JSONObject();
            return room.toStringFull();
        } else {
            return "{\"error\":\"This room is private\"}";
        }
    }

    //tested
    @GetMapping("stats/top")
    public String roomStatsTop(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.isAvailabe(userByPrincipal)) {
            List<String> top = messagesTopRepository.findTopByRoomGroupByUserOrderByCountDesc(room, PageRequest.of(0, 10)).stream().map(MessagesTop::toString).collect(Collectors.toList());
            JSONObject response = new JSONObject();
            response.put("top", top);
            return response.toString();
        } else {
            return "{\"error\":\"This room is private\"}";
        }
    }

    @PostMapping("all-messages")
    public String roomAllMessages(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id, @RequestBody AllMessagesRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.isAvailabe(userByPrincipal)) {
            JSONObject response = new JSONObject();
            List<Message> messages = messageRepository.findAllByRoomAndTimeBetween(room, requestBody.getFrom(), requestBody.getTo());
            Collections.sort(messages);
            response.put("messages", messages.stream().map(Message::toString).collect(Collectors.toList()));
            return response.toString();
        } else {
            return "{\"error\":\"This room is private\"}";
        }
    }

    // tested
    @PostMapping("/invite")
    public String roomInvite(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id, @RequestBody InviteRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.getAdmin() == userByPrincipal) {
            CustomUser invitedUser = customUserRepository.findByUsernameIs(requestBody.getUsername());
            if (invitedUser != null) {
                room.addUser(invitedUser);
                roomRepository.save(room);
            }
            return "{\"message\": \"User was successfully invited\"}";
        } else {
            return "{\"error\":\"Only owner can invite\"}";
        }
    }

    // tested
    @PostMapping("/make_private")
    public String roomMakePrivate(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.getAdmin() == userByPrincipal) {
            room.setIsPrivate(true);
            roomRepository.save(room);
            return "{\"message\": \"Room is now private\"}";
        } else {
            return "{\"error\":\"Only owner can invite\"}";
        }
    }

    // tested
    @PostMapping("/change_name")
    public String roomChangeName(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id, @RequestBody ChangeRoomNameRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.getAdmin() == userByPrincipal) {
            room.setName(requestBody.getName());
            roomRepository.save(room);
            return "{\"message\": \"Room name was successfully changed\"}";
        } else {
            return "{\"error\":\"Only owner can invite\"}";
        }
    }
}
