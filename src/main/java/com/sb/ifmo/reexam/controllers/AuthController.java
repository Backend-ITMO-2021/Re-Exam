package com.sb.ifmo.reexam.controllers;

import org.json.JSONObject;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping("/user")
    public String user(@AuthenticationPrincipal OAuth2User principal) {
        JSONObject response = new JSONObject();
        response.put("name", (String) principal.getAttribute("username"));
        response.put("id", (int) principal.getAttribute("id"));

        return response.toString();
    }
}
