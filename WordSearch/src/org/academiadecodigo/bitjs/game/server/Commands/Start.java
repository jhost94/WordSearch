package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;

import java.io.IOException;


public class Start implements CommandHandler{
    @Override
    public void handle(PlayerHandler player) {
        if (player.getServer().getListPlayers().size() < 2){
            player.getSocketWriter().write("You need at least two players to start the game!");
            player.getSocketWriter().flush();
        }
        for (PlayerHandler p : player.getServer().getListPlayers()){
            try{
                p.start();
            } catch (IOException e){
                System.out.println(e.getMessage());
            }
        }
    }
}
