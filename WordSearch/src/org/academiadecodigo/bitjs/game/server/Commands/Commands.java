package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;

import java.io.OutputStream;
import java.io.PrintWriter;

public class Commands implements CommandHandler{

    private String list;

    public Commands(){
        init();
    }

    private void init(){
        list =  "COMMANDS: \n\r" +
                "/start - starts the game immediately\n\r" +
                "/help - shows you the instructions\n\r" +
                "/pointslist - shows you the current player list and their score\n\r" +
                "/commands - shows you command list\n\r" +
                "/quit - quits the game\n\r";
    }
    @Override
    public void handle(PlayerHandler player){
        player.getSocketWriter().write(this.list);
        player.getSocketWriter().flush();
    }
}
