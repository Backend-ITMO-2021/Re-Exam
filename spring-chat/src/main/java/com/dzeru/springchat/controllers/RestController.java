package com.dzeru.springchat.controllers;

import com.dzeru.springchat.dto.ChatterStatDto;
import com.dzeru.springchat.dto.RoomMessagesDto;
import com.dzeru.springchat.dto.TimeFilterDto;
import com.dzeru.springchat.dto.TopChattersDto;
import com.dzeru.springchat.entities.Message;
import com.dzeru.springchat.entities.Room;
import com.dzeru.springchat.entities.User;
import com.dzeru.springchat.repos.MessageRepo;
import com.dzeru.springchat.repos.RoomRepo;
import com.dzeru.springchat.repos.UserAndCount;
import com.dzeru.springchat.repos.UserRepo;
import com.dzeru.springchat.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("api")
public class RestController {
    private static final Logger logger = LoggerFactory.getLogger(RestController.class);

    @Autowired
    public RoomRepo roomRepo;

    @Autowired
    public UserRepo userRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private MessageRepo messageRepo;

    @GetMapping("/{roomId}/messages/{username}")
    @ResponseBody
    public RoomMessagesDto getAllMessagesFromSpecificUserInRoom(@PathVariable("roomId") long room_id, @PathVariable("username") String username) {
        logger.info("REST: getAllMessagesFromSpecificUser");
        User user = (User) userService.loadUserByUsername(username);
        Room room = roomRepo.getById(room_id);

        return new RoomMessagesDto(messageRepo.findAllByRoomAndSender(room, user));
    }

    @GetMapping("/{roomId}/stats/top")
    @ResponseBody
    public TopChattersDto getTopChattersByMessagesCountInRoom(@PathVariable("roomId") long room_id) {
        logger.info("REST: getTopChattersByMessagesCountInRoom");
        Room room = roomRepo.getById(room_id);
        List<UserAndCount> userAndCounts = userRepo.findTopChattersByMessagesCountInRoom(room);
        TopChattersDto topChattersDto = new TopChattersDto();
        for (UserAndCount userAndCount : userAndCounts) {
            topChattersDto.addChatterStatDto(new ChatterStatDto(userAndCount.getUser(), userAndCount.getMessageCount()));
        }

        return topChattersDto;
    }

    @GetMapping("/{roomId}/all-messages")
    @ResponseBody
    public RoomMessagesDto getAllCurrentMessagesFilteredByDateInRoom(@PathVariable("roomId") long room_id, @RequestBody(required = false) TimeFilterDto timeFilterDto) {
        Room room = roomRepo.getById(room_id);

        if (timeFilterDto != null) {
            logger.info("REST: getAllCurrentMessagesFilteredByDateInRoom: dateFilter");
            Timestamp after = new Timestamp(timeFilterDto.from);
            Timestamp before = new Timestamp(timeFilterDto.to);
            return new RoomMessagesDto(messageRepo.findAllByRoomAndTodAfterAndTodBefore(room, after, before));
        }

        return new RoomMessagesDto(messageRepo.findAllByRoom(room));
    }
}
