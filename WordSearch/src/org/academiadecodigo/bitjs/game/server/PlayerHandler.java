package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.Color;
import org.academiadecodigo.bitjs.game.InitialMenu;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class PlayerHandler implements Runnable {
    private Socket clientSocket;
    private PrintWriter socketWriter;
    private BufferedReader socketReader;
    private GameManager server;
    private InitialMenu initialMenu;
    private String name = null;
    private Color color;
    private Color boardColor;
    //private String color;

    public PlayerHandler(Socket clientSocket, GameManager server) {
        this.clientSocket = clientSocket;
        this.server = server;
        setUpIOStreams();
    }

    @Override
    public void run() {
        try {
            init();
            closeStreams();
        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private void init() throws IOException {
        initializeMenu();

        chooseCredentials();

        start();


    }

    private void initializeMenu() throws IOException {
        initialMenu = new InitialMenu(clientSocket);
        initialMenu.printTitle(socketWriter);
    }

    private void chooseCredentials() {
        chooseName();
        chooseColor();
    }

    private void start() {
        printGameSet();

        while (clientSocket.isConnected()) {
            
        }

    }

    private void chooseName() {
        String setName = initialMenu.chooseName();

        while (server.checkUsernameExists(setName)) {
            socketWriter.println(ServerMessages.usernameError);
            socketWriter.flush();
            setName = initialMenu.chooseName();
        }
        this.name = setName;
    }

    /*private void chooseColor(){
        String setColor = Colors.values()[initialMenu.chooseColor()].getColor();

        while (server.checkColorExists(setColor)) {
            socketWriter.println(ServerMessages.colorError2);
            socketWriter.flush();
            setColor = Colors.values()[initialMenu.chooseColor()].getColor();
        }
        this.color = setColor;
    }*/

    private void chooseColor(){
        Color setColor = Color.values()[initialMenu.chooseColor()];

        while (server.checkColorExists(setColor)) {
            socketWriter.println(ServerMessages.colorError2);
            socketWriter.flush();
            setColor = Color.values()[initialMenu.chooseColor()];
        }
        this.color = setColor;
        this.boardColor = color.getPlayerBoundColor(color);
    }

    public void setUpIOStreams() {
        try {
            socketWriter = new PrintWriter(clientSocket.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private void closeStreams() throws IOException {
        clientSocket.close();
    }

    private void printGameSet() {
        initialMenu.printTitle(socketWriter);
        server.printBoard();
        printQuestions();
    }

    public void printQuestions() {
        //Prints the questions of the game
        socketWriter.print(" " + "\n" +
                Color.BOARD_YELLOW.concat(" 1. ") + Color.BOLD.concat(" Is a protocol of LINK Layer") + "          " + Color.BOARD_YELLOW.concat(" 2. ") + Color.BOLD.concat(" Architecture of a computer network") + "\n" +
                Color.BOARD_YELLOW.concat(" 3. ") + Color.BOLD.concat(" It´s one of the four pillars of OOP") + "  " + Color.BOARD_YELLOW.concat(" 4. ") + Color.BOLD.concat(" Creator of the World Wide Web") + "\n" +
                Color.BOARD_YELLOW.concat(" 5. ") + Color.BOLD.concat(" It´s a verb") + "                          " + Color.BOARD_YELLOW.concat(" 6. ") + Color.BOLD.concat(" It's a checked exception") + "\n");
        socketWriter.flush();
    }


    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    public PrintWriter getSocketWriter() {
        return socketWriter;
    }
}
