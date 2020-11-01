package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;

public class PointsList implements CommandHandler{
    @Override
    public void handle(PlayerHandler player) {
        for (PlayerHandler p : player.getServer().getListPlayers()){
            player.getSocketWriter().write( p.getColor().concat(p.getName()) + ": " + p.getPoints() + "\n\r");
            player.getSocketWriter().flush();
        }
    }
}
