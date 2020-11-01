package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.Color;
import org.academiadecodigo.bitjs.game.server.PlayerHandler;
import org.academiadecodigo.bitjs.game.server.ServerMessages;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

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
