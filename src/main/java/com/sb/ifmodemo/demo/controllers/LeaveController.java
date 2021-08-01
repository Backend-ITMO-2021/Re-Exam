package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.GameRepository;
import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/leave")
public class LeaveController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;


    @PostMapping
    public String leave(
            @RequestHeader(value = "authorization", required = false, defaultValue = "unknown") String authorization
    ){
        var user = ifmoUserRepository.findFirstBySessionIs(authorization).filter(p -> p.getGame() != 0L);
        if(user.isPresent()) {
            user.get().setGame(0L);
            ifmoUserRepository.save(user.get());
            return "{\"result\":\"OK\"}";
        } else {
            return "{\"result\":\"no active game\"}";
        }
    }

}
