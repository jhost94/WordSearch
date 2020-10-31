package org.academiadecodigo.bitjs;

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
        listPlayers.add(player);
    }

    public void addPlayer(PlayerHandler player) {
        listPlayers.add(player);
    }

    public void removePlayer(PlayerHandler player) {
        listPlayers.remove(player);
    }

    public boolean checkUsernameExists(String username) {
        synchronized (this) {
            for (PlayerHandler player : listPlayers) {
                if (username.equals(player.getName())) {
                    return true;
                }
            }
        }

        return false;
    }
}
