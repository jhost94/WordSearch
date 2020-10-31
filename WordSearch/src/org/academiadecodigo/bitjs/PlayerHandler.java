package org.academiadecodigo.bitjs;

import java.io.IOException;
import java.net.Socket;

public class PlayerHandler implements Runnable{
    private Socket clientSocket;
    private GameManager server;
    private String name;
    private String color;

    public PlayerHandler(Socket clientSocket, GameManager server) {
        this.clientSocket = clientSocket;
        this.server = server;
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

    }

    public String getName() {
        return name;
    }
}
