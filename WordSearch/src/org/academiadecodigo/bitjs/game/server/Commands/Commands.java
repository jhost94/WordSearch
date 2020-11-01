package org.academiadecodigo.bitjs.game.server.Commands;

import org.academiadecodigo.bitjs.game.server.PlayerHandler;
import org.academiadecodigo.bitjs.game.server.ServerMessages;

public class Commands implements CommandHandler{

    private String list;

    public Commands(){
        init();
    }

    private void init(){
        this.list = ServerMessages.LIST;
    }

    @Override
    public void handle(PlayerHandler player){
        player.getSocketWriter().write(this.list);
        player.getSocketWriter().flush();
    }
}
