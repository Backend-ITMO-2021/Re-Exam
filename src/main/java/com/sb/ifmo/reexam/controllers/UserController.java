package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.CustomUser;
import com.sb.ifmo.reexam.data.CustomUserRepository;
import com.sb.ifmo.reexam.requests.ChangeRoomNameRequest;
import com.sb.ifmo.reexam.requests.ChangeUsernameRequest;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private CustomUserRepository customUserRepository;

    // tested
    @GetMapping("/username")
    public String username(@AuthenticationPrincipal OAuth2User principal) {

        JSONObject response = new JSONObject();
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        response.put("username", userByPrincipal.getUsername());

        return response.toString();
    }

    // tested
    @PostMapping("/username/change")
    public String usernameChange(@AuthenticationPrincipal OAuth2User principal, @RequestBody ChangeUsernameRequest requestBody) {
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        if (customUserRepository.findByUsernameIs(requestBody.getUsername()) != null) {
            return "{\"error\": \"User with this nickname already exists\"}";
        }
        userByPrincipal.setUsername(requestBody.getUsername());
        customUserRepository.save(userByPrincipal);
        return "{\"message\": \"User was successfully invited\"}";
    }
}
