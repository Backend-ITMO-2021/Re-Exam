package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.IfmoUser;
import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.json.*;


@RestController
@RequestMapping("/register")
public class RegistrationController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;

    @PostMapping
    public String createUser(@RequestBody String userJson){

        JSONObject obj = new JSONObject(userJson);
        String name = obj.getString("username");
        String password = obj.getString("password");
        var userExists = ifmoUserRepository.findByNameIs(name).size() > 0;
        if(userExists) {
            return "{\"result\":\"user exists\"}";
        } else {
            var user = new IfmoUser();
            user.setName(name);
            user.setPassword(password);
            ifmoUserRepository.save(user);
            return "{\"result\":\"user created\"}";
        }
    }

}
