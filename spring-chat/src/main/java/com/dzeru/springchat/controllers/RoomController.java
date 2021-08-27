package com.dzeru.springchat.controllers;

import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import com.dzeru.springchat.repos.RoomRepo;
import com.dzeru.springchat.repos.UserRepo;
import com.dzeru.springchat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class RoomController {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomRepo roomRepo;

    @RequestMapping(value = "/room/{id}", method = RequestMethod.GET)
    public String room(@PathVariable("id") long room_id, Principal principal, Model model) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        Room room = (Room) roomRepo.findById(room_id).get(); //TODO возможные проблемы


        model.addAttribute("room", room);
        model.addAttribute("user", user);
        model.addAttribute("participants", room.getParticipants());

        return "room";
    }

    @RequestMapping(value = "/room/{id}/load-users", method = RequestMethod.GET)
    @ResponseBody
    public List<User> getMembers(@PathVariable("id") long room_id) {
        List<User> allUsers = userRepo.findAll();
        Set<User> roomMembers = roomRepo.findById(room_id).get().getParticipants();
        List<User> potentialMembers = new ArrayList<>();
        for (User user : allUsers) {
            if (!roomMembers.contains(user)) potentialMembers.add(user);
        }

        return potentialMembers;
    }

    @PostMapping("/room/{id}/add-user")
    public String addRoom(@PathVariable("id") long room_id, Principal principal, String username) {
        User user = (User) userService.loadUserByUsername(username);
        Room room = (Room) roomRepo.findById(room_id).get();
        user.addRoom(room);
        userRepo.save(user);

        return "redirect:/room/{id}";
    }

    @PostMapping("/room/{roomId}/change-name")
    public String changeName(@PathVariable("roomId") long room_id, Principal principal, String newName) {
        Room room = (Room) roomRepo.findById(room_id).get();
        room.setName(newName);
        roomRepo.save(room);

        return "redirect:/room/{roomId}";
    }
}
