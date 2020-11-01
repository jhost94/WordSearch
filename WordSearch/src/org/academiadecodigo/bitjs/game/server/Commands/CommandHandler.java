package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;


public interface CommandHandler {

    void handle(PlayerHandler player);
}
