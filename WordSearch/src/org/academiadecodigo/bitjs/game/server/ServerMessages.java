package org.academiadecodigo.bitjs.game.server;

import org.academiadecodigo.bitjs.game.Color;

public class ServerMessages {
    public static final String NAME_QUESTION = "\uD83D\uDC49 Choose your name: ";
    public static final String COLOR_QUESTION = "\uD83D\uDC49 Choose your color: ";
    public static final String GUESS = "\n\r✏️️  WRITE YOUR GUESS!!";
    public static final String CORRECT_ANSWER = "Your answer is correct! ✅";
    public static final String WRONG_ANSWER = "Your answer is wrong! ❌";
    public static final String ALREADY_ANSWERED = "Choose another question! This one is already taken! \uD83E\uDD37\u200D️";
    public static final String WRONG_IMPLEMENTATION = "Your answer is not written correctly! ❌ Write again! ";
    public static final String USERNAME_ERROR = "Username already taken! Choose another username! \uD83E\uDD37\u200D";
    public static final String COLOR_ERROR = "That color doesn't exist! Choose another color! \uD83E\uDD37\u200D";
    public static final String COLOR_ERROR_2 = "Color already taken! Choose another color! \uD83E\uDD37\u200D";
    public static final String INVALID_COMMAND = "That command doesn't exist! ❌ Use \uD83D\uDC49"+ Color.BOLD.concat("/commands") + " to see all the available commands. \n\r";
    public static final String INTRODUCTION = "Type \uD83D\uDC49 " + Color.BOLD.concat("/help") + " to see the game instructions, while you wait for other players! \uD83D\uDE03";
    public static final String NEW_PLAYER = " has joined the game! \uD83D\uDE00 \uD83D\uDE4C";
    public static final String PLAYER_NAMES = " other players are waiting to start the game! ⏱️\n\r";
    public static final String NOT_ENOUGH_PLAYERS = "You need at least two players to start the game! \uD83E\uDD37\u200D️\n\r\n\r";
    public static final String NOT_ALL_READY = "⏱  Waiting for other players...\n\r\n\r";
    public static final String[] COLOR_OPTIONS = {"Blue \uD83D\uDC99", "Green \uD83D\uDC9A", "Orange \uD83E\uDDE1", "Pink \uD83D\uDC97"};
    public static final String LIST = "\n\r\n\r\uD83D\uDC49 " + Color.BOLD.concat("COMMANDS: ") + "\uD83D\uDC48\n\r\n\r" +
            "▶️  /start - starts the game immediately\n\r" +
            "\uD83D\uDE29 /help - shows you the instructions\n\r" +
            "\uD83C\uDFC6 /pointslist - shows you the current player list and their score\n\r" +
            "\uD83C\uDFAE /commands - shows you command list\n\r" +
            "⏹️  /quit - quits the game\n\r\n\r";
    public static final String INSTRUCTIONS  = "\n\r \uD83E\uDD13 " + Color.BOLD.concat("INSTRUCTIONS:") + " \uD83E\uDD13 \n\r\n\r" +
            "In the game you search for the correct answers in the Word Soup \uD83C\uDF72 and compete with other players.\n\r" +
            "In order to answer, a question, first you select it by typing its number, followed by its coordinates (row,column).\n\r" +
            "From right to the left and from top to bottom.\n\r\n\r" +
            "\uD83D\uDC47 It should look like this \uD83D\uDC47\n\r\n\r" +
            Color.BOLD.concat("<question number> <first row>,<first column> <last row>,<last column>\n\r\n\r") +
            Color.BOLD.concat("Example: 2 1,10 1,12\n\r\n\r") +
            "There are only horizontal and vertical words.\n\r" +
            "To get the list of commands type:" + Color.BOLD.concat("/commands") + " or to get this message again type: " + Color.BOLD.concat("/help\n\r") +
            "\uD83C\uDF40 " + Color.BOLD.concat("GOOD LUCK!") +  " \uD83C\uDF40\n\r" +
            "\n\r";

}
