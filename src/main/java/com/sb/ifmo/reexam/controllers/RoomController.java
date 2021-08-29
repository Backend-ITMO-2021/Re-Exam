package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.*;
import com.sb.ifmo.reexam.requests.AllMessagesRequest;
import com.sb.ifmo.reexam.requests.ChangeRoomNameRequest;
import com.sb.ifmo.reexam.requests.InviteRequest;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

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

    @GetMapping("stats/top")
    public String roomStatsTop(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.isAvailabe(userByPrincipal)) {
            Set<MessagesTop> top = messagesTopRepository.findTop10ByRoomGroupByUserOrderByCountDesc(room);
            JSONObject response = new JSONObject();
            JSONArray topJSONArray = new JSONArray();
            for (MessagesTop messagesTop : top) {
                topJSONArray.put(messagesTop);
            }
            response.put("top", topJSONArray);
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
            List messages = messageRepository.findAllByRoomAndTimeBetween(room, requestBody.getFrom().atStartOfDay(), requestBody.getTo().atTime(23, 59, 59));
            JSONArray messagesJSON = new JSONArray(messages);
            response.put("messages", messagesJSON);
            return response.toString();
        } else {
            return "{\"error\":\"This room is private\"}";
        }
    }

    @PostMapping("/invite")
    public String roomInvite(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id, @RequestBody InviteRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.getAdmin() == userByPrincipal) {
            CustomUser invitedUser = customUserRepository.findByUsernameIs(requestBody.getUsername());
            if (invitedUser != null) {
                room.addUser(invitedUser);
            }
            return "{\"message\": \"User was successfully invited\"}";
        } else {
            return "{\"error\":\"Only owner can invite\"}";
        }
    }

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
