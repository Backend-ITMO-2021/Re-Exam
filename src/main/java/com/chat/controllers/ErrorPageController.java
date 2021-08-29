package com.chat.controllers;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author
 */
@Controller
public class ErrorPageController {
    @GetMapping("/myerror")
    public String privateRoom(Map<String, String> model) {
        model.put("error", model.get("error"));
        return "error";
    }
}
