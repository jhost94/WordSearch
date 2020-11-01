package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.AnswerCoordinate;
import org.academiadecodigo.bitjs.game.Color;
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
    private boolean ready;


    public PlayerHandler(Socket clientSocket, GameManager server) {
        this.clientSocket = clientSocket;
        this.server = server;
        this.ready = false;
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

        waitingRoom();

        start();
    }

    private void waitingRoom() throws IOException {
        server.notifyNewPlayerJoined(this);

        socketWriter.println(ServerMessages.INTRODUCTION);
        socketWriter.flush();

        while (!server.isStarted()) {

            String message = socketReader.readLine();
            commandVerification(message);

            if (ready) {
                break;
            }

        }
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
        System.out.println("start");

        socketWriter.println(ServerMessages.GUESS);
        socketWriter.flush();

        while (!clientSocket.isClosed()) {

            receiveAnswers();
        }

    }

    public void receiveAnswers() throws IOException {
        String message = socketReader.readLine();
        commandVerification(message);

        String[] splitMessage = message.split(" ");
        AnswerCoordinate defaultAnswer = AnswerCoordinate.ANSWER_1;

        //This block verifies if the first element of the string array is a question number
        if (!defaultAnswer.verifyQuestionNumber(splitMessage[0])) {
            System.out.println("devia escrever o erro");
            socketWriter.println(ServerMessages.WRONG_IMPLEMENTATION);
            socketWriter.flush();
            return;
        }

        int questionNumber = Integer.parseInt(splitMessage[0]);
        AnswerCoordinate answerCoordinate = AnswerCoordinate.values()[Integer.parseInt(splitMessage[0]) - 1];

        if (!answerCoordinate.isAnswered()) {
            if (!verifyLength(questionNumber, splitMessage.length)) {
                System.out.println("1 if");
                socketWriter.println(ServerMessages.WRONG_IMPLEMENTATION);
                socketWriter.flush();
                return;
            }

            if (!verifyAnswerCoordinates(splitMessage, answerCoordinate)) {
                System.out.println("2 if");
                socketWriter.println(ServerMessages.WRONG_ANSWER);
                socketWriter.flush();
                return;
            }

            answerCoordinate.setAnswered(true);

            socketWriter.println(ServerMessages.CORRECT_ANSWER + "\n\r");
            socketWriter.flush();
            server.broadcast(splitMessage, this);
            return;
        }

        socketWriter.println("\n" + ServerMessages.ALREADY_ANSWERED);
    }

    private void commandVerification(String message) throws IOException {
        if (message == null) {
            Command.QUIT.getCommandHandler().handle(this);
        }
        if (message.startsWith("/")) {
            Command.checkCommand(message, this);
            receiveAnswers();
            return;
        }
    }

    private boolean verifyAnswerCoordinates(String[] splitMessage, AnswerCoordinate answerCoordinate) {
        if (!(splitMessage[1].equals(answerCoordinate.getInitialCoordinate()) && splitMessage[2].equals(answerCoordinate.getFinalCoordinate()))) {
            return false;
        }
        return true;
    }

    private boolean verifyLength(int questionNumber, int length) {
        if (length != 3) {
            return false;
        }
        return true;
    }

    private void chooseName() {
        String setName = initialMenu.chooseName();

        while (server.checkUsernameExists(setName)) {
            socketWriter.println(ServerMessages.USERNAME_ERROR);
            socketWriter.flush();
            setName = initialMenu.chooseName();
        }
        this.name = setName;
    }

    private void chooseColor() {
        Color setColor = Color.values()[initialMenu.chooseColor()];

        while (server.checkColorExists(setColor)) {
            socketWriter.println(ServerMessages.COLOR_ERROR_2);
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

    public void printInitialGameSet() {
        initialMenu.printTitle(socketWriter);
        server.initialPrintBoard(this);
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

    public GameManager getServer() {
        return server;
    }

    public int getPoints() {
        return points;
    }

    public Color getBoardColor() {
        return boardColor;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public InitialMenu getInitialMenu() {
        return initialMenu;
    }
}
