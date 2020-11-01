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

public class PlayerHandler implements Runnable, Comparable {
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
        this.points = 0;
    }

    @Override
    public void run() {
        try {
            init();

        } catch (IOException ex) {
            ex.getStackTrace();
        }
    }

    private void init() throws IOException {
        setUpIOStreams();

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

        /*socketWriter.println(ServerMessages.GUESS);
        socketWriter.flush();*/

        while (!clientSocket.isClosed()) {
            receiveAnswers();

            if (allQuestionsAnswered()) {
                //print points and winner to board
                server.endGame();
                break;
            }
        }

    }

    public void receiveAnswers() throws IOException {


        String message = socketReader.readLine();

        if(commandVerification(message)){
            return;
        }

        String[] splitMessage = message.split(" ");
        AnswerCoordinate defaultAnswer = AnswerCoordinate.ANSWER_DEFAULT;

        //This block verifies if the first element of the string array is a question number
        if (!defaultAnswer.verifyQuestionNumber(splitMessage[0])) {
            System.out.println("devia escrever o erro");
            socketWriter.println(ServerMessages.WRONG_IMPLEMENTATION);
            socketWriter.flush();
            return;
        }

        int questionNumber = Integer.parseInt(splitMessage[0]);
        AnswerCoordinate answerCoordinate = AnswerCoordinate.values()[questionNumber - 1];

        if (!answerCoordinate.isAnswered()) {
            if (!verifyLength(splitMessage.length)) {
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
            addPoints(answerCoordinate.getPoints());
            System.out.println(points);

            socketWriter.println(ServerMessages.CORRECT_ANSWER + "\n\r");
            socketWriter.flush();
            server.broadcast(splitMessage, this);


            return;
        }

        socketWriter.println("\n" + ServerMessages.ALREADY_ANSWERED);
    }

    private boolean commandVerification(String message) throws IOException {
        if (message == null) {
            Command.QUIT.getCommandHandler().handle(this);
        }
        if (message.startsWith("/")) {
            Command.checkCommand(message, this);
            //receiveAnswers();
            return true;
        }
        return false;
    }

    private boolean verifyAnswerCoordinates(String[] splitMessage, AnswerCoordinate answerCoordinate) {
        if (!(splitMessage[1].equals(answerCoordinate.getInitialCoordinate()) && splitMessage[2].equals(answerCoordinate.getFinalCoordinate()))) {
            return false;
        }
        return true;
    }

    private boolean verifyLength(int length) {
        if (length != 3) {
            return false;
        }
        return true;
    }

    private boolean allQuestionsAnswered() {
        for (AnswerCoordinate answer : AnswerCoordinate.values()) {
            if (!answer.isAnswered()) {
                return false;
            }
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

    public void setUpIOStreams() throws IOException {
        socketWriter = new PrintWriter(clientSocket.getOutputStream());
        socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void closeStreams() throws IOException {
        clientSocket.close();
    }

    public void printInitialGameSet() {
        initialMenu.printTitle(socketWriter);
        server.initialPrintBoard(this);
        printQuestions();
        socketWriter.println(ServerMessages.GUESS);
        socketWriter.flush();
    }

    public void printQuestions() {
        //Prints the questions of the game
        socketWriter.print(" " + "\n" +
                Color.BOARD_YELLOW.concat(" 1. ") + Color.BOLD.concat(" Is a protocol of LINK Layer - 5 POINTS") + "            " + Color.BOARD_YELLOW.concat(" 2. ") + Color.BOLD.concat(" Architecture of a computer network - 5 POINTS") + "\n" +
                Color.BOARD_YELLOW.concat(" 3. ") + Color.BOLD.concat(" It´s one of the four pillars of OOP - 15 POINTS") + "   " + Color.BOARD_YELLOW.concat(" 4. ") + Color.BOLD.concat(" Creator of the World Wide Web - 10 POINTS") + "\n" +
                Color.BOARD_YELLOW.concat(" 5. ") + Color.BOLD.concat(" It´s a HTTP verb - 10 POINTS") + "                      " + Color.BOARD_YELLOW.concat(" 6. ") + Color.BOLD.concat(" It's a checked exception - 10 POINTS") + "\n");
        socketWriter.flush();
    }

    public void addPoints(int points) {
        this.points += points;
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

    @Override
    public int compareTo(Object o) {
        PlayerHandler comparator = (PlayerHandler) o;
        return  comparator.getPoints() - this.getPoints();
    }
}
