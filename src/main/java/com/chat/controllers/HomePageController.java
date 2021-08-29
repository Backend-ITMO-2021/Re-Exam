package com.chat.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.repository.RoomRepository;
import com.chat.services.RoomService;
import com.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author
 */
@Controller
public class HomePageController {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserService userService;


    @GetMapping("/")
    public String index() {
        return "redirect:/main";
    }

    @GetMapping("/registration")
    public String registration() {
        return "registration";
    }

    @GetMapping("/main")
    public String home(Map<String, Object> model) {
        User user = userService.getCurrentUser();
        List<Room> privateRooms = user.getRooms().stream().filter(Room::getPrivate).collect(Collectors.toList());
        List<Room> publicRooms = ((List<Room>) roomRepository.findAll()).stream().filter(r -> !r.getPrivate()).collect(
                Collectors.toList());

        model.put("rooms", publicRooms);
        model.put("private_rooms", privateRooms);

        model.put("user", userService.getCurrentUser().getUsername());

        return "main";
    }

    @PostMapping("/change_name")
    public String change_name(@RequestParam String name) {
        userService.changeUsername(name);
        return "redirect:/main";
    }

    @GetMapping("restricted")
    public String restricted() {
        return "";
    }


    @GetMapping("/create_room/send_message")
    public String createRoom(Model model) {
        return "create_room";
    }

    @PostMapping("/create_room")
    public String createPostRoom(@RequestParam String title, Model model) {
        Room room = new Room(title, userService.getCurrentUser(), new ArrayList<>());

        User user = userService.getCurrentUser();
        roomService.addUser(room, user);

        return "redirect:/main";
    }

    @GetMapping("/create_room")
    public String createRoom() {
        return "create_room";
    }
}
