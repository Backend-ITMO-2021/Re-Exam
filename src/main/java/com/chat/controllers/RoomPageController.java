package com.chat.controllers;

import java.util.List;
import java.util.Map;

import com.chat.domain.Room;
import com.chat.domain.User;
import com.chat.domain.dto.MessageDto;
import com.chat.repository.MessageRepository;
import com.chat.repository.RoomRepository;
import com.chat.services.RoomService;
import com.chat.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author
 */
@Controller
public class RoomPageController {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @GetMapping("/enter_room/{room}")
    public String enterRoom(@PathVariable String room, RedirectAttributes attributes) {
        Room currentRoom = roomService.getRoomById(room);
        if (!currentRoom.getPrivate() || roomService.isActionByMemberOfChat(currentRoom)) {
            return "redirect:/room/" + room;
        } else {
            attributes.addFlashAttribute("error", "Приватная комната, у вас нет доступа");
            return "redirect:/myerror";
        }
    }


    @PostMapping("/{roomId}/change")
    public String change(
            @PathVariable String roomId,
            RedirectAttributes attributes)
    {
        Room room = roomService.getRoomById(roomId);
        if (!roomService.isActionByAdmin(room)) {
            attributes.addFlashAttribute("error", "Приватная комната, у вас нет доступа");
            return "redirect:/myerror";
        }
        room.setPrivate(!room.getPrivate());
        roomRepository.save(room);
        return "redirect:/room/" + roomId;
    }

    @GetMapping("/room/{room}")
    public String getRoom(
            Map<String, Object> model,
            @PathVariable String room,
            @ModelAttribute("error") String error)
    {
        Room currentRoom = roomService.getRoomById(room);
        List<MessageDto> messagesByRoom = messageRepository.findAllMessagesByRoom(currentRoom);
        model.put("messages", messagesByRoom);
        model.put("roomId", room);
        model.put("admin", roomService.getRoomAdmin(currentRoom).getUsername());
        model.put("is_admin",
                roomService.getRoomAdmin(currentRoom).getId().equals(userService.getCurrentUser().getId()));
        model.put("users", roomService.getUsers(currentRoom));
        model.put("switched_type", currentRoom.getPrivate() ? "Публичная" : "Приватная");
        model.put("type", currentRoom.getPrivate() ? "Приватная" : "Публичная");
        model.put("room_name", currentRoom.getTitle());
        return "room";
    }

    @PostMapping("/{room}/send_message")
    public String addMessage(
            @RequestParam String text,
            @PathVariable String room,
            RedirectAttributes redirectAttributes)
    {
        User user = userService.getCurrentUser();
        Room currentRoom = roomService.getRoomById(room);
        if (currentRoom.getPrivate() && !roomService.isActionByMemberOfChat(currentRoom)) {
            redirectAttributes.addFlashAttribute("error", "Вы не член комнаты");
            return "redirect:/myerror";
        }
        roomService.addMessage(currentRoom, user, text);

        return "redirect:/room/{room}";
    }

    @PostMapping("/{roomId}/add_user")
    public String addUser(
            @PathVariable String roomId,
            @RequestParam String username,
            RedirectAttributes redirectAttributes)
    {
        try {
            User user = (User) userService.loadUserByUsername(username);
            Room room = roomService.getRoomById(roomId);
            roomService.addUser(room, user);
        } catch (Exception e) {
            redirectAttributes.addAttribute("error", "Нет такого пользователя");
            return "redirect:/myerror";

        }
        return "redirect:/room/" + roomId;
    }

    @PostMapping("/{roomId}/change_name")
    public String changeName(
            @PathVariable String roomId,
            @RequestParam String name,
            RedirectAttributes redirectAttributes)
    {
        if (roomService.isActionByAdmin(roomService.getRoomById(roomId))) {
            redirectAttributes.addFlashAttribute("error", "Вы не член комнаты");
            return "redirect:/myerror";
        }
        roomService.changeRoomName(roomId, name);
        return "redirect:/room/" + roomId;
    }
}
