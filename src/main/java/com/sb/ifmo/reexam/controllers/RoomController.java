package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.Room;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController("rooms")
public class RoomController {
    @GetMapping("/list")
    public Set<Room> roomSet(@AuthenticationPrincipal OAuth2User principal) {
        return principal.getAttribute("rooms");
    }
    @GetMapping("/{id}")
    public Set<Room> room(@AuthenticationPrincipal OAuth2User principal, @PathVariable int id) {
        return principal.getAttribute("rooms");
    }
}
