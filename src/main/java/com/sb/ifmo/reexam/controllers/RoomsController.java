package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.*;
import com.sb.ifmo.reexam.requests.CreateRoomRequest;
import com.sb.ifmo.reexam.requests.InviteRequest;
import org.json.JSONObject;
import org.springframework.beans.NullValueInNestedPathException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping(value = "/rooms", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class RoomsController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private MessageRepository messageRepository;

    @GetMapping()
    public String roomSet(@AuthenticationPrincipal OAuth2User principal) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Set<Room> availableRooms = new HashSet<>();
        try {
            availableRooms.addAll(userByPrincipal.getJoinedRooms());
        } catch (NullValueInNestedPathException ignored) {
        }
        try {
            availableRooms.addAll(userByPrincipal.getOwnedRooms());
        } catch (NullValueInNestedPathException ignored) {
        }
        try {
            availableRooms.addAll(roomRepository.findAllByIsPrivate(false));
        } catch (NullValueInNestedPathException ignored) {
        }
        return availableRooms.toString();
    }

    @PostMapping("/create")
    public String room(@AuthenticationPrincipal OAuth2User principal, @RequestBody CreateRoomRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room newRoom = new Room(requestBody.getName(), requestBody.isPrivate(), userByPrincipal);
        roomRepository.save(newRoom);

        JSONObject response = new JSONObject();
        response.put("room_id", newRoom.getId());

        return response.toString();
    }

    @GetMapping("/{room_id}")
    public String room(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        Room room = roomRepository.findById(room_id);
        if (room.isAvailabe(userByPrincipal)) {
            return room.toString();
        } else {
            return "{\"error\":\"This room is private\"}";
        }
    }

    // TODO: create new room and get its id
    @PostMapping("/{room_id}/invite")
    public String room(@AuthenticationPrincipal OAuth2User principal, @PathVariable long room_id, @RequestBody InviteRequest requestBody) {
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
}
