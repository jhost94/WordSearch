package org.academiadecodigo.bitjs;

public enum Colors {
    TITLE("\u001b[33m"),
    BLUE("\u001b[34;1m"),
    GREEN("\u001b[36;1m"),
    ORANGE("\u001b[31;1m"),
    PINK("\u001b[35;1m"),
    YELLOW("\u001b[30m\u001b[43m"),
    WHITE("\u001b[30m\u001b[47m"),
    BOLD("\u001b[1m");

    private String color;
    private String close;

    Colors(String color){
        this.color = color;
        this.close = "\u001b[0m";
    }

    public String concat(String letter){
        return this.color + letter + this.close;
    }

    public String getColor() {
        return color;
    }
}