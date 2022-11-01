package com.mob.backend.service.impl.pk;

import com.mob.backend.consumer.WebSocketServer;
import com.mob.backend.service.pk.StartGameService;
import org.springframework.stereotype.Service;

import java.util.WeakHashMap;

@Service
public class StartGameServiceImpl implements StartGameService {
    @Override
    public String startGame(Integer aId, Integer aBotId, Integer bId, Integer bBotId) {
        System.out.println("start game: " + aId + " " + bId);
        WebSocketServer.startGame(aId, aBotId, bId, bBotId);
        return "start game success";
    }
}
