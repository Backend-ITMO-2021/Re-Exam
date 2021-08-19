package com.sb.ifmo_reexam.reexam.controllers;

import com.sb.ifmo_reexam.reexam.data.CurrentUser;
import com.sb.ifmo_reexam.reexam.data.User;
import com.sb.ifmo_reexam.reexam.data.UserPrincipal;
import com.sb.ifmo_reexam.reexam.data.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId()).orElseThrow();
    }
}
