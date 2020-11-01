package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;

import java.io.IOException;


public class Quit implements CommandHandler{
    @Override
    public void handle(PlayerHandler player) {
        try{
            player.closeStreams();
            player.getServer().removePlayer(player);
            for (PlayerHandler p : player.getServer().getListPlayers()){
                if (p.getName() == player.getName()){
                    continue;
                }
                p.getSocketWriter().write(player.getColor().concat(p.getName()) + " has left the game.");
            }
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
