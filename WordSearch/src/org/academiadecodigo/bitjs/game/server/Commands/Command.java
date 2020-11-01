package org.academiadecodigo.bitjs.game.server.Commands;

public enum Command {
    START("/start", new Start()),
    HELP("/help", new Help()),
    POINTSLIST("pointslist", new PointsList()),
    COMMANDS("/commands", new Commands()),
    QUIT("/quit", new Quit());

    private String command;
    private CommandHandler commandHandler;

    Command(String command, CommandHandler commandHandler){
        this.command = command;
        this.commandHandler = commandHandler;
    }

    public CommandHandler getCommandHandler(){
        return this.commandHandler;
    }

    public String getCommand(){
        return this.command;
    }
}
