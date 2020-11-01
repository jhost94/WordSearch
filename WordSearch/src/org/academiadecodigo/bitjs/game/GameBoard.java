package org.academiadecodigo.bitjs.game;

import java.io.PrintWriter;

public class GameBoard {
    public static final String[] ANSWER_1 = {" B ", " S ", " U "};
    public static final String[] ANSWER_2 = {" C ", " L ", " I ", " E ", " N ", " T ", " S ", " E ", " R ", " V ", " E ", " R "};
    public static final String[] ANSWER_3 = {" E ", " C ", " N ", " A ", " T ", " I ", " R ", " E ", " H ", " N ", " I "};
    public static final String[] ANSWER_4 = {" T ", " I ", " M "};
    public static final String[] ANSWER_5 = {" D ", " E ", " L ", " E ", " T ", " E "};
    public static final String[] ANSWER_6 = {" I ", " O ", " E ", " X ", " C ", " E ", " P ", " T ", " I ", " O ", " N "};

    volatile public static String[][] board = {
            {"                   ", Color.BOARD_YELLOW.concat(" 1 "), Color.BOARD_YELLOW.concat(" 2 "), Color.BOARD_YELLOW.concat(" 3 "), Color.BOARD_YELLOW.concat(" 4 "), Color.BOARD_YELLOW.concat(" 5 "), Color.BOARD_YELLOW.concat(" 6 "), Color.BOARD_YELLOW.concat(" 7 "), Color.BOARD_YELLOW.concat(" 8 "), Color.BOARD_YELLOW.concat(" 9 "), Color.BOARD_YELLOW.concat("10 "), Color.BOARD_YELLOW.concat("11 "), Color.BOARD_YELLOW.concat("12 "), Color.BOARD_YELLOW.concat("13 "), Color.BOARD_YELLOW.concat("14 "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 1  "), Color.BOARD_WHITE.concat(" L "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" A "), Color.BOARD_WHITE.concat(" G "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" G "), Color.BOARD_WHITE.concat(" H "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 2  "), Color.BOARD_WHITE.concat(ANSWER_2[0]), Color.BOARD_WHITE.concat(ANSWER_2[1]), Color.BOARD_WHITE.concat(ANSWER_2[2]), Color.BOARD_WHITE.concat(ANSWER_2[3]), Color.BOARD_WHITE.concat(ANSWER_2[4]), Color.BOARD_WHITE.concat(ANSWER_2[5]), Color.BOARD_WHITE.concat(ANSWER_2[6]), Color.BOARD_WHITE.concat(ANSWER_2[7]), Color.BOARD_WHITE.concat(ANSWER_2[8]), Color.BOARD_WHITE.concat(ANSWER_2[9]), Color.BOARD_WHITE.concat(ANSWER_2[10]), Color.BOARD_WHITE.concat(ANSWER_2[11]), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(ANSWER_5[0]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 3  "), Color.BOARD_WHITE.concat(" L "), Color.BOARD_WHITE.concat(ANSWER_6[0]), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" G "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" C "), Color.BOARD_WHITE.concat(ANSWER_5[1]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 4  "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(ANSWER_6[1]), Color.BOARD_WHITE.concat(" B "), Color.BOARD_WHITE.concat(" U "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" M "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" L "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" B "), Color.BOARD_WHITE.concat(ANSWER_5[2]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 5  "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(ANSWER_6[2]), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" P "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" A "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" U "), Color.BOARD_WHITE.concat(ANSWER_5[3]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 6  "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(ANSWER_6[3]), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" A "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" L "), Color.BOARD_WHITE.concat(" P "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(ANSWER_5[4]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 7  "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(ANSWER_6[4]), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" U "), Color.BOARD_WHITE.concat(" M "), Color.BOARD_WHITE.concat(" A "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" C "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" X "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(ANSWER_5[5]), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 8  "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(ANSWER_6[5]), Color.BOARD_WHITE.concat(" O "), Color.BOARD_WHITE.concat(" O "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" B "), Color.BOARD_WHITE.concat(" A "), Color.BOARD_WHITE.concat(" U "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" X "), Color.BOARD_WHITE.concat(" S "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 9  "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(ANSWER_6[6]), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" C "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" M "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" O "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" I "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 10 "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(ANSWER_6[7]), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(" C "), Color.BOARD_WHITE.concat(ANSWER_1[0]), Color.BOARD_WHITE.concat(ANSWER_1[1]), Color.BOARD_WHITE.concat(ANSWER_1[2]), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(ANSWER_4[0]), Color.BOARD_WHITE.concat(" B "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" T "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 11 "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(ANSWER_6[8]), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" P "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(ANSWER_4[1]), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" L "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 12 "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(ANSWER_6[9]), Color.BOARD_WHITE.concat(" P "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" L "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" U "), Color.BOARD_WHITE.concat(" D "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(ANSWER_4[2]), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" C "), Color.BOARD_WHITE.concat(" R "), Color.BOARD_WHITE.concat(" N "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 13 "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(ANSWER_6[10]), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" B "), Color.BOARD_WHITE.concat(" O "), Color.BOARD_WHITE.concat(" T "), Color.BOARD_WHITE.concat(" N "), Color.BOARD_WHITE.concat(" H "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" S "), Color.BOARD_WHITE.concat(" P "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" N "), " \n"},
            {"               " + Color.BOARD_YELLOW.concat(" 14 "), Color.BOARD_WHITE.concat(" E "), Color.BOARD_WHITE.concat(ANSWER_3[0]), Color.BOARD_WHITE.concat(ANSWER_3[1]), Color.BOARD_WHITE.concat(ANSWER_3[2]), Color.BOARD_WHITE.concat(ANSWER_3[3]), Color.BOARD_WHITE.concat(ANSWER_3[4]), Color.BOARD_WHITE.concat(ANSWER_3[5]), Color.BOARD_WHITE.concat(ANSWER_3[6]), Color.BOARD_WHITE.concat(ANSWER_3[7]), Color.BOARD_WHITE.concat(ANSWER_3[8]), Color.BOARD_WHITE.concat(ANSWER_3[9]), Color.BOARD_WHITE.concat(ANSWER_3[10]), Color.BOARD_WHITE.concat(" I "), Color.BOARD_WHITE.concat(" U "), " \n"},
    };


    public static void printGameCard(PrintWriter socketWriter) {
        for (int row = 0; row < board.length; row++) {        // scheme.length returns the number of rows (14)
            for (int col = 0; col < board[0].length; col++) {  // scheme[0].length returns the number of cols (11)
                socketWriter.print(board[row][col]);
                socketWriter.flush();
            }
        }
    }

    public static void horizontalAnswer(int row, int col1, int col2, Color color, int answerNumber) {
        String[] lettersToShow = answerTranslation(answerNumber);
        int letterIndex = 0;
        for (int col = col1; col <= col2; col++) {
            board[row][col] = color.concat(lettersToShow[letterIndex++]);
        }
    }

    public static void verticalAnswer(int col, int row1, int row2, Color color, int answerNumber) {
        String[] lettersToShow = answerTranslation(answerNumber);
        int letterIndex = 0;
        for (int row = row1; row <= row2; row++) {
            board[row][col] = color.concat(lettersToShow[letterIndex++]);
        }
    }

    private static String[] answerTranslation(int answerNumber) {
        switch (answerNumber) {
            case 1:
                return ANSWER_1;
            case 2:
                return ANSWER_2;
            case 3:
                return ANSWER_3;
            case 4:
                return ANSWER_4;
            case 5:
                return ANSWER_5;
            case 6:
                return ANSWER_6;
        }
        return null;
    }
}
