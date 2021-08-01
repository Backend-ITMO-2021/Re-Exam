package com.sb.ifmodemo.demo.controllers;

import com.sb.ifmodemo.demo.data.Game;
import com.sb.ifmodemo.demo.data.GameRepository;
import com.sb.ifmodemo.demo.data.IfmoUserRepository;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/turn")
public class TurnController {

    @Autowired
    private IfmoUserRepository ifmoUserRepository;

    @Autowired
    private GameRepository gameRepository;

    private boolean checkWin(String field, Character check) {
        char[] x = field.toCharArray();
        return (
            (x[0] == x[1] && x[1] == x[2] && x[0] == check) ||
            (x[3] == x[4] && x[4] == x[5] && x[3] == check) ||
            (x[6] == x[7] && x[7] == x[8] && x[6] == check) ||
            (x[0] == x[3] && x[3] == x[6] && x[0] == check) ||
            (x[1] == x[4] && x[4] == x[7] && x[1] == check) ||
            (x[2] == x[5] && x[5] == x[8] && x[2] == check) ||
            (x[0] == x[4] && x[4] == x[8] && x[0] == check) ||
            (x[2] == x[4] && x[4] == x[6] && x[2] == check)
        );
    }

    @PostMapping
    public String makeTrun(
            @RequestHeader(value = "authorization", required = false, defaultValue = "unknown") String authorization,
            @RequestBody String turnJson
    ){

        JSONObject obj = new JSONObject(turnJson);
        int turn = obj.getInt("turn");
        var p1 = ifmoUserRepository.findFirstBySessionIs(authorization).filter(p -> p.getGame() != 0L);
        if (p1.isPresent()) {
            var game = gameRepository.findById(p1.get().getGame());
            var gameTurns = game.get().getTurns();
            var isMyTurn = game.get().getWinner().isEmpty() && (
                    (p1.get().getId() == game.get().getPlayer1() && gameTurns % 2 == 0) ||
                    (p1.get().getId() == game.get().getPlayer2() && gameTurns % 2 == 1)
            );
            if(isMyTurn && turn >= 0 && turn < 9) {

                var curChar = game.get().getTurns() % 2 == 0 ? 'x' : '0';

                var field = game.get().getField();
                char[] x = field.toCharArray();
                if(x[turn] != '*') {
                    return "{\"result\":\"field non empty\"}";
                }
                x[turn] = curChar;
                field = String.valueOf(x);

                game.get().setTurns(game.get().getTurns() + 1);
                game.get().setField(field);

                if(checkWin(field, 'x')) {
                    game.get().setWinner("X");
                } else if(checkWin(field, '0')) {
                    game.get().setWinner("0");
                }

                gameRepository.save(game.get());
                return "{\"result\":\"OK\"}";
            } else {
                return "{\"result\":\"not your turn\"}";
            }
        } else {
            return "{\"result\":\"active game not found\"}";
        }
    }

}
