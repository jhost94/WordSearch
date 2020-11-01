package org.academiadecodigo.bitjs.game;

public enum AnswerCoordinate {
    ANSWER_1("10,6", "10,8", "horizontal",false),
    ANSWER_2("2,1", "2,12", "horizontal",false),
    ANSWER_3("14,2", "14,12", "horizontal",false),     //ESTAAAAAAAA
    ANSWER_4("10,10", "12,10", "vertical",false),
    ANSWER_5("2,14", "7,14", "vertical",false),
    ANSWER_6("3,2", "13,2", "vertical",false);    //ESTAAAAAAAA

    private String initialCoordinate;
    private String finalCoordinate;
    private String orientation;
    private boolean isAnswered;

    AnswerCoordinate(String initialCoordinate, String finalCoordinate, String orientation, boolean isAnswered) {
        this.initialCoordinate = initialCoordinate;
        this.finalCoordinate = finalCoordinate;
        this.orientation = orientation;
        this.isAnswered = isAnswered;
    }

    public String getInitialCoordinate() {
        return initialCoordinate;
    }

    public String getFinalCoordinate() {
        return finalCoordinate;
    }

    public String getOrientation() {
        return orientation;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

}
