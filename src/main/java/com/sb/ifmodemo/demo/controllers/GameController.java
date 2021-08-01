package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.Game;
import com.sb.ifmodemo.demo.data.GameRepository;
import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;

    @Autowired
    private GameRepository gameRepository;

    @PostMapping
    public String createGame(
        @RequestHeader(value = "authorization", required = false, defaultValue = "unknown") String authorization,
        @RequestBody String gameJson
    ){

        JSONObject obj = new JSONObject(gameJson);
        long player2 = obj.getLong("player");
        var p1 = ifmoUserRepository.findFirstBySessionIs(authorization).filter(p -> p.getGame() == 0L);
        var p2 = ifmoUserRepository.findById(player2).filter(p -> p.getGame() == 0L);
        if (p1.isPresent() && p2.isPresent()) {
            var game = new Game();
            game.setPlayer1(p1.get().getId());
            game.setPlayer2(p2.get().getId());
            game.setField("*********");
            game.setTurns(0);
            game.setWinner("");
            var g = gameRepository.save(game);
            p1.get().setGame(g.getId());
            p2.get().setGame(g.getId());
            ifmoUserRepository.save(p1.get());
            ifmoUserRepository.save(p2.get());
            return "{\"result\":\"game created\"}";
        } else {
            return "{\"result\":\"can't create game\"}";
        }
    }

}
