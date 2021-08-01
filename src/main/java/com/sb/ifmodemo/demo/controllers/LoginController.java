package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;

    @PostMapping
    public String loginUser(@RequestBody String userJson){

        JSONObject obj = new JSONObject(userJson);
        String name = obj.getString("username");
        String password = obj.getString("password");
        var user = ifmoUserRepository.findFirstByNameIsAndPasswordIs(name, password);
        if(user.isEmpty()) {
            return "{\"result\":\"user not found\"}";
        } else {
            UUID uuid = UUID.randomUUID();
            String uuidAsString = uuid.toString();
            user.get().setSession(uuidAsString);
            ifmoUserRepository.save(user.get());
            return "{\"session\":\"" + uuidAsString + "\"}";
        }
    }

}
