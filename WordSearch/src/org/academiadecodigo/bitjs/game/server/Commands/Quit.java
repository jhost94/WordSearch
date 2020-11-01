package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;
import java.io.IOException;

public class Quit implements CommandHandler {
    @Override
    public void handle(PlayerHandler player) {
        try {
            player.closeStreams();
            player.getServer().removePlayer(player);
            for (PlayerHandler p : player.getServer().getListPlayers()) {
                p.getSocketWriter().write(player.getColor().concat(player.getName()) + " has left the game! \n\r");
                p.getSocketWriter().flush();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
