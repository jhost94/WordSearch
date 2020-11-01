package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

public class Help implements CommandHandler {
    private String help;

    public Help() {
        init();
    }

    private void init() {
        this.help = "\n\rINSTRUCTIONS: \n\r\n\r" +
                "In the game you search for the correct answers in the Word Soup and compete with other players.\n\r" +
                "In order to answer, a question, first you select it by typing its number, followed by its coordinates (row,column).\n\r" +
                "From right to the left and from top to bottom.\n\r\n\r" +
                "It should look like this:\n\r" +
                "<question number> <first row>,<first column> <last row>,<last column>\n\r" +
                "Example: 2 1,10 1,12\n\r\n\r" +
                "There are only horizontal and vertical words.\n\r" +
                "To get the list of commands type: /commands or to get this message again type: /help\n\r" +
                "GOOD LUCK!\n\r" +
                "\n\r";
    }

    @Override
    public void handle(PlayerHandler player) {
        player.getSocketWriter().write(help);
        player.getSocketWriter().flush();
    }
}
