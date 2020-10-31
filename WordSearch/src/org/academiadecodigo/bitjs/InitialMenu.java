package org.academiadecodigo.bitjs;

import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

public class InitialMenu {
    //TODO MENU
    private String name;
    private String color;
    private Prompt menuPrompt;
    private ServerMessages message;
    private GameManager gameManager;

    public static void main(String[] args) {
        InitialMenu menu= new InitialMenu(new GameManager());
        menu.chooseName();
    }
    public InitialMenu(GameManager gameManager){
        this.menuPrompt=new Prompt(System.in,System.out);
        this.gameManager= gameManager;
    }


    public void chooseName (){
        StringInputScanner nameQuestion= new StringInputScanner();
        nameQuestion.setMessage(ServerMessages.nameQuestion);
        String playerName=this.menuPrompt.getUserInput(nameQuestion);


    }


}
