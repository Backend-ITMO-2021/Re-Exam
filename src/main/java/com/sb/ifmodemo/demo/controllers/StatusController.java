package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.GameRepository;
import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;

    @Autowired
    private GameRepository gameRepository;

    @GetMapping
    public String provideStatus(
        @RequestHeader(value = "authorization", required = false, defaultValue = "unknown") String authorization
    ) {

        var loggedInUser = ifmoUserRepository.findFirstBySessionIs(authorization);
        JSONObject response = new JSONObject();
        if(loggedInUser.isEmpty()) {
            response.put("authorized", false);
        } else {
            var notAssignedUsers = ifmoUserRepository.findAllByGameIs(0L);
            var users = new JSONArray();
            for(var user: notAssignedUsers) {
                if(user.getId() == loggedInUser.get().getId()) continue;
                var inner = new JSONObject();
                inner.put("name", user.getName());
                inner.put("id", user.getId());
                users.put(inner);
            }

            var gameId = loggedInUser.get().getGame();
            if(gameId != 0L) {
                var game = gameRepository.findById(gameId);
                var jGame = new JSONObject();
                jGame.put("field", game.get().getField());
                if(!game.get().getWinner().isEmpty()) {
                    jGame.put("winner", game.get().getWinner());
                    jGame.put("play", false);
                } else {
                    var turn = game.get().getTurns();
                    if(
                        (loggedInUser.get().getId() == game.get().getPlayer1() && turn % 2 == 0) ||
                        (loggedInUser.get().getId() == game.get().getPlayer2() && turn % 2 == 1)
                    ) {
                        jGame.put("play", true);
                    } else {
                        jGame.put("play", false);
                    }
                }
                response.put("game", jGame);
            }

            response.put("authorized", true);
            response.put("users", users);
        }
        return response.toString();
    }

}