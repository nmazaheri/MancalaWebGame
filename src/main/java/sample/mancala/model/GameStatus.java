package sample.mancala.model;

/**
 * Determines if game should continue or if game end condition has been reached.
 */
public enum GameStatus {
    PLAYING(""),
    TIE("Tie game"),
    PLAYER_ONE_WIN("Player One Wins!"),
    PLAYER_TWO_WIN("Player Two Wins!");

    private String message;

    GameStatus(String message) {
        this.message = message;
    }

    public static GameStatus handleGameEnd(int playerOneScore, int playerTwoScore) {
        if (playerOneScore == playerTwoScore) {
            return TIE;
        }
        if (playerOneScore > playerTwoScore) {
            return PLAYER_ONE_WIN;
        }
        return PLAYER_TWO_WIN;
    }

    public String getMessage() {
        return message;
    }
}
