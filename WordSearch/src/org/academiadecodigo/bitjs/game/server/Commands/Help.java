package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;
import org.academiadecodigo.bitjs.game.server.ServerMessages;

public class Help implements CommandHandler {
    private String help;

    public Help() {
        init();
    }

    private void init() {
        this.help = ServerMessages.INSTRUCTIONS;
    }

    @Override
    public void handle(PlayerHandler player) {
        player.getSocketWriter().write(help);
        player.getSocketWriter().flush();
    }
}
