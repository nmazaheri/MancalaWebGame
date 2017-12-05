package sample.jsp;

/**
 *
 */
public enum GameResult {
    PLAYING,
    TIE,
    PLAYER_ONE_WIN,
    PLAYER_TWO_WIN;

    public static GameResult handleGameEnd(int playerOne, int playerTwo) {
        if (playerOne == playerTwo) {
            return TIE;
        }
        if (playerOne > playerTwo) {
            return PLAYER_ONE_WIN;
        }
        return PLAYER_TWO_WIN;
    }
}
