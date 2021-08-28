package com.sb.ifmo.reexam.controllers;

import com.sb.ifmo.reexam.data.CustomUser;
import com.sb.ifmo.reexam.data.CustomUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/me", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    @Autowired
    private CustomUserRepository customUserRepository;

    @GetMapping("/username")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        JSONObject response = new JSONObject();
        CustomUser userByPrincipal = customUserRepository.findByEmailIs(principal.getAttribute("email"));
        response.put("username", userByPrincipal.getUsername());

        return response.toString();
    }
}
