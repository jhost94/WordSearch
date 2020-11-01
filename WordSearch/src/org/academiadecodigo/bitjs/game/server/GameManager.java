package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.Color;
import org.academiadecodigo.bitjs.game.GameBoard;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class GameManager {
    public static final int DEFAULT_PORT = 10000;
    private ServerSocket serverSocket;
    private List<PlayerHandler> listPlayers;
    private ExecutorService fixedThreadPool;

    public GameManager() {
        listPlayers = Collections.synchronizedList(new LinkedList<>());
        fixedThreadPool = Executors.newFixedThreadPool(4);
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

        PlayerHandler player = new PlayerHandler(clientSocket, this);
        fixedThreadPool.submit(player);
        addPlayer(player);
    }

    public void printBoard() {
        for (PlayerHandler player : listPlayers) {
            GameBoard.printGameCard(player.getSocketWriter());
        }
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
}
