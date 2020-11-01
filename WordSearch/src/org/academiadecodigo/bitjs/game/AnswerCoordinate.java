package org.academiadecodigo.bitjs.game;

public enum AnswerCoordinate {
    ANSWER_1("1", "10,6", "10,8", "horizontal",false),
    ANSWER_2("2","2,1", "2,12", "horizontal",false),
    ANSWER_3("3","14,2", "14,12", "horizontal",false),     //ESTAAAAAAAA
    ANSWER_4("4","10,10", "12,10", "vertical",false),
    ANSWER_5("5","2,14", "7,14", "vertical",false),
    ANSWER_6("6","3,2", "13,2", "vertical",false);    //ESTAAAAAAAA

    private String questionNumber;
    private String initialCoordinate;
    private String finalCoordinate;
    private String orientation;
    private boolean isAnswered;

    AnswerCoordinate(String questionNumber, String initialCoordinate, String finalCoordinate, String orientation, boolean isAnswered) {
        this.questionNumber = questionNumber;
        this.initialCoordinate = initialCoordinate;
        this.finalCoordinate = finalCoordinate;
        this.orientation = orientation;
        this.isAnswered = isAnswered;
    }

    public boolean verifyQuestionNumber (String questionNumber){
        for (AnswerCoordinate answerCoordinate : AnswerCoordinate.values()) {
            if(answerCoordinate.questionNumber.equals(questionNumber)){
                return true;
            }
        }
        return false;
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
