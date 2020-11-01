package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.AnswerCoordinate;
import org.academiadecodigo.bitjs.game.Color;
import org.academiadecodigo.bitjs.game.GameBoard;
import org.academiadecodigo.bitjs.game.InitialMenu;
import org.academiadecodigo.bitjs.game.server.Commands.Command;

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
    private int points;
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

    public void start() throws IOException {
        printInitialGameSet();

        while (!clientSocket.isClosed()) {
            socketWriter.println("\nWRITE YOUR GUESS!!");
            socketWriter.flush();
            receiveAnswers();
        }

    }

    public void receiveAnswers() throws IOException {
        String message = socketReader.readLine();

        if (message.startsWith("/")){
            Command.checkCommand(message,this);
            receiveAnswers();
            return;
        }
        System.out.println(message);
        
        //System.out.println(GameBoard.ANSWER_6[0] + " " + GameBoard.ANSWER_6[10]);
        //System.out.println(GameBoard.board[3][2] + " " + GameBoard.board[13][2]);

        String[] splitMessage = message.split(" ");
        AnswerCoordinate answerCoordinate = AnswerCoordinate.values()[Integer.parseInt(splitMessage[0]) - 1];


        if (!answerCoordinate.isAnswered()) {

            if (!verifyLengthAndQuestionNumber(Integer.parseInt(splitMessage[0]), splitMessage.length)) {
                //invalid answer message
                System.out.println("1 if");
                socketWriter.println(ServerMessages.wrongImplementation);
                socketWriter.flush();
                receiveAnswers();
                return;
            }

            if (!verifyAnswerCoordinates(splitMessage, answerCoordinate)) {
                // incorrect answer message
                System.out.println("2 if");
                socketWriter.println(ServerMessages.wrongAnswer);
                socketWriter.flush();
                receiveAnswers();
                return;
            }

            answerCoordinate.setAnswered(true);

            System.out.println("to print board");
            server.broadcast(splitMessage, this);
            return;
        }

        socketWriter.println("\n" + ServerMessages.alreadyAnswered);
        //Commands(message);
    }

//    private boolean verifyValidAnswer(String[] splitMessage) {
//        System.out.println("0 = " + splitMessage[0] + "1 = " + splitMessage[1] + "2 = " + splitMessage[2] + "3 = " + splitMessage[3]);
//
//        return verifyLengthAndQuestionNumber(Integer.parseInt(splitMessage[0]), splitMessage.length);
//    }

    private boolean verifyAnswerCoordinates(String[] splitMessage, AnswerCoordinate answerCoordinate) {
        if (!(splitMessage[1].equals(answerCoordinate.getInitialCoordinate()) && splitMessage[2].equals(answerCoordinate.getFinalCoordinate()))) {
            return false;
        }

        return true;
    }

    private boolean verifyLengthAndQuestionNumber(int questionNumber, int length) {
        if (length != 3) {
            return false;
        }
        if (questionNumber >= 7 || questionNumber <= 0) {
            return false;
        }
        return true;
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
    private void chooseColor() {
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

    public void closeStreams() throws IOException {
        clientSocket.close();
    }

    private void printInitialGameSet() {
        initialMenu.printTitle(socketWriter);
        server.initialPrintBoard(this);
        printQuestions();
        //server.printBoard();
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

    public GameManager getServer(){
        return server;
    }

    public int getPoints(){
        return points;
    }
}
