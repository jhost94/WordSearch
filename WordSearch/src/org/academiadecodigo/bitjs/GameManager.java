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
    private List<PlayerHandler> players;
    private ExecutorService fixedThreadPool;

    public GameManager() {
        players = Collections.synchronizedList(new LinkedList<>());
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

        fixedThreadPool.submit(new PlayerHandler(clientSocket, this));
    }
}
