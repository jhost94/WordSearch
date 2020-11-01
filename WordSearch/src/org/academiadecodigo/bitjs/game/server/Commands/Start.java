package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;
import org.academiadecodigo.bitjs.game.server.ServerMessages;

import java.io.IOException;


public class Start implements CommandHandler{
    @Override
    public void handle(PlayerHandler player) {
        if (player.getServer().getListPlayers().size() < 2){
            player.getSocketWriter().write(ServerMessages.NOT_ENOUGH_PLAYERS);
            player.getSocketWriter().flush();
            return;
        }

        player.setReady(true);

        if (!player.getServer().seePlayersReady()) {
            player.getSocketWriter().write(ServerMessages.NOT_ALL_READY);
            player.getSocketWriter().flush();
            return;
        }

        player.getServer().startGame();
    }
}
