package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.AnswerCoordinate;
import org.academiadecodigo.bitjs.game.Color;
import org.academiadecodigo.bitjs.game.GameBoard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {
    public static final int DEFAULT_PORT = 10000;
    private ServerSocket serverSocket;
    private List<PlayerHandler> listPlayers;
    private ExecutorService fixedThreadPool;
    volatile private boolean started;

    public GameManager() {
        listPlayers = Collections.synchronizedList(new LinkedList<>());
        fixedThreadPool = Executors.newFixedThreadPool(4);
        this.started = false;
    }

    public void init() {
        try {
            serverSocket = new ServerSocket(DEFAULT_PORT);

            while (true) {

                waitPlayers(serverSocket);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitPlayers(ServerSocket serverSocket) throws IOException {
        Socket clientSocket = serverSocket.accept();

        if (started) {
            PrintWriter serverFull = new PrintWriter(clientSocket.getOutputStream());

            serverFull.println(ServerMessages.SERVER_FULL);
            serverFull.flush();

            return;
        }

        PlayerHandler player = new PlayerHandler(clientSocket, this);
        fixedThreadPool.submit(player);
        addPlayer(player);
    }

    public void printBoard() {
        for (PlayerHandler player : listPlayers) {
            player.getSocketWriter().println("\033[H\033[2J"); //Condition to clear the screen
            player.getSocketWriter().flush();
            player.getInitialMenu().printTitle(player.getSocketWriter());
            GameBoard.printGameCard(player.getSocketWriter());
            player.printQuestions();
            player.getSocketWriter().println(ServerMessages.GUESS);
            player.getSocketWriter().flush();
        }
    }

    public void initialPrintBoard(PlayerHandler player) {
        GameBoard.printGameCard(player.getSocketWriter());
    }

    public void addPlayer(PlayerHandler player) {
        listPlayers.add(player);
    }

    public void removePlayer(PlayerHandler player) {
        listPlayers.remove(player);
    }

    public synchronized boolean checkUsernameExists(String username) {   //true = existe
        for (PlayerHandler player : listPlayers) {
            if (username.equals(player.getName())) {
                return true;
            }
        }

        return false;
    }

    public synchronized boolean checkColorExists(Color color) {   //true = existe
        for (PlayerHandler player : listPlayers) {
            if (color == player.getColor()) {
                return true;
            }
        }

        return false;
    }

    public synchronized void broadcast(String[] splitMessage, PlayerHandler sender) {
        AnswerCoordinate answerCoordinate = AnswerCoordinate.values()[Integer.parseInt(splitMessage[0]) - 1];

        GameBoard.verifyOrientation(splitMessage, answerCoordinate.getOrientation(), sender);
        printBoard();
    }

    public synchronized void notifyNewPlayerJoined(PlayerHandler player) {
        String playerName = player.getColor().concat(player.getName());

        for (PlayerHandler listPlayer : listPlayers) {
            if (listPlayer == player) {
                player.getSocketWriter().println(getPlayersNames());
                player.getSocketWriter().flush();
                continue;
            }
            listPlayer.getSocketWriter().println(playerName + ServerMessages.NEW_PLAYER);
            listPlayer.getSocketWriter().flush();
        }

    }

    private synchronized String getPlayersNames() {
        return "\n\r" + (listPlayers.size() - 1) + ServerMessages.PLAYER_NAMES;
    }

    public synchronized boolean seePlayersReady() {
        for (PlayerHandler listPlayer : listPlayers) {
            if (!listPlayer.isReady()) {
                return false;
            }
        }
        return true;
    }

    public synchronized void startGame() {
        started = true;
        for (PlayerHandler listPlayer : listPlayers) {
            listPlayer.printInitialGameSet();
        }
    }

    public synchronized void endGame() throws IOException {
        Collections.sort(listPlayers, PlayerHandler::compareTo);


        for (PlayerHandler player : listPlayers) {
            player.getSocketWriter().println("\033[H\033[2J"); //Condition to clear the screen
            player.getSocketWriter().flush();

            int index = 1;
            String specialMessage = "";
            for (PlayerHandler listPlayer : listPlayers) {
                if (index == 1) {
                    specialMessage = "\uD83C\uDFC6  " + Color.BOLD.concat("THE WINNER IS:") + "  \uD83C\uDFC6\n\r";
                } else if (index == 2) {
                    specialMessage = "\n\r\uD83D\uDC4E  " + Color.BOLD.concat("THE LOSERS ARE:") + "  \uD83D\uDC4E\n\r";
                }

                player.getSocketWriter().println(specialMessage + index + ". " + listPlayer.getColor().concat(listPlayer.getName()) + " \uD83D\uDC49 " + listPlayer.getPoints() + " points ");
                player.getSocketWriter().flush();
                specialMessage = "";
                index++;
            }
            player.closeStreams();
        }
        //started = false;
        listPlayers.removeAll(listPlayers);
    }

    public List<PlayerHandler> getListPlayers() {
        return listPlayers;
    }

    public boolean isStarted() {
        return started;
    }
}
