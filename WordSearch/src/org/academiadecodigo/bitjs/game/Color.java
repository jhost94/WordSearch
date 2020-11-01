package org.academiadecodigo.bitjs.game;

public enum Color {
    TITLE("\u001b[33m"),
    CHAR_BLUE("\u001b[34;1m"),
    CHAR_GREEN("\u001b[36;1m"),
    CHAR_ORANGE("\u001b[31;1m"),
    CHAR_PINK("\u001b[35;1m"),
    /** TEST */
    BOARD_BLUE("\u001b[30m\u001b[44m"),
    BOARD_GREEN("\u001b[30m\u001b[42m"),
    BOARD_ORANGE("\u001b[30m\u001b[48;5;9m"),
    BOARD_PINK("\u001b[30m\u001b[45m"),
    BOARD_YELLOW("\u001b[30m\u001b[43m"),
    BOARD_WHITE("\u001b[30m\u001b[47m"),
    BOLD("\u001b[1m");

    private String color;
    private String close;

    Color(String color){
        this.color = color;
        this.close = "\u001b[0m";
    }

    public String concat(String letter){
        return this.color + letter + this.close;
    }

    public Color getPlayerBoundColor(Color color){
        return Color.values()[color.ordinal() + 4];
    }
}