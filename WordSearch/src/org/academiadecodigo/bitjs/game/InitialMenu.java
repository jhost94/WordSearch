package org.academiadecodigo.bitjs.game;

import org.academiadecodigo.bitjs.game.server.ServerMessages;
import org.academiadecodigo.bootcamp.Prompt;
import org.academiadecodigo.bootcamp.scanners.menu.MenuInputScanner;
import org.academiadecodigo.bootcamp.scanners.string.StringInputScanner;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;

public class InitialMenu {
    private Prompt menuPrompt;

    public InitialMenu(Socket clientSocket) throws IOException {
        this.menuPrompt = new Prompt(clientSocket.getInputStream(), new PrintStream(clientSocket.getOutputStream()));
    }

    public String chooseName() {
        StringInputScanner nameQuestion = new StringInputScanner();
        nameQuestion.setMessage(ServerMessages.nameQuestion);
        return this.menuPrompt.getUserInput(nameQuestion);
    }

    public int chooseColor() {
        String[] options = ServerMessages.colorOptions;
        MenuInputScanner scanner = new MenuInputScanner(options);
        scanner.setMessage(ServerMessages.colorQuestion);
        scanner.setError(ServerMessages.colorError);
        return menuPrompt.getUserInput(scanner);
    }

    public void printTitle(PrintWriter socketWriter) {
        /*socketWriter.print("       __        __            _   ____                      _     \n" +
                "        \\ \\      / ___  _ __ __| | / ___|  ___  __ _ _ __ ___| |__  \n" +
                "         \\ \\ /\\ / / _ \\| '__/ _` | \\___ \\ / _ \\/ _` | '__/ __| '_ \\ \n" +
                "          \\ V  V | (_) | | | (_| |  ___) |  __| (_| | | | (__| | | |\n" +
                "           \\_/\\_/ \\___/|_|  \\__,_| |____/ \\___|\\__,_|_|  \\___|_| |_|\n" +
                "                                                             \n"


        );*/

        socketWriter.print(Color.TITLE.concat(
                "██╗    ██╗██████╗██████╗██████╗     ██████████████╗█████╗██████╗ ████████╗  ██╗\n" +
                        "██║    ████╔═══████╔══████╔══██╗    ██╔════██╔════██╔══████╔══████╔════██║  ██║\n" +
                        "██║ █╗ ████║   ████████╔██║  ██║    ████████████╗ █████████████╔██║    ███████║\n" +
                        "██║███╗████║   ████╔══████║  ██║    ╚════████╔══╝ ██╔══████╔══████║    ██╔══██║\n" +
                        "╚███╔███╔╚██████╔██║  ████████╔╝    ████████████████║  ████║  ██╚████████║  ██║\n" +
                        " ╚══╝╚══╝ ╚═════╝╚═╝  ╚═╚═════╝     ╚══════╚══════╚═╝  ╚═╚═╝  ╚═╝╚═════╚═╝  ╚═╝" +
                        "\n\n"
        ));

        socketWriter.flush();
    }

}
