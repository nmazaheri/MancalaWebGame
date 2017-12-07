package sample.jsp.model;

/**
 *
 */
public enum GameResult {
    PLAYING,
    TIE,
    PLAYER_ONE_WIN,
    PLAYER_TWO_WIN;

    public static GameResult handleGameEnd(int playerOneScore, int playerTwoScore) {
        if (playerOneScore == playerTwoScore) {
            return TIE;
        }
        if (playerOneScore > playerTwoScore) {
            return PLAYER_ONE_WIN;
        }
        return PLAYER_TWO_WIN;
    }
}
