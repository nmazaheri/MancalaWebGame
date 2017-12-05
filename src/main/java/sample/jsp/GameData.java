package sample.jsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static sample.jsp.Player.PLAYER_ONE;
import static sample.jsp.Player.PLAYER_TWO;

/**
 *
 */
public class GameData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameData.class);
    private final static int INITIAL_STONE_COUNT = 6;

    /**
     * Defines the amount of stones in the pits
     */
    private int[] pitStones;

    private Player currentPlayer;

    public GameData() {
        this.pitStones = new int[]{0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT};
        currentPlayer = PLAYER_ONE;
    }

    public int[] getPitStones() {
        return pitStones;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setNextPlayer() {
        if (currentPlayer.equals(PLAYER_ONE)) {
            currentPlayer = PLAYER_TWO;
        } else {
            currentPlayer = PLAYER_ONE;
        }
    }

    /**
     * Check if a player has won the game
     */
    public GameResult checkForWin() {
        if (endGameCondition()) {
            int playerOneScore = pitStones[0];
            int playerTwoScore = pitStones[7];
            return GameResult.handleGameEnd(playerOneScore, playerTwoScore);
        }
        return GameResult.PLAYING;
    }

    private boolean endGameCondition() {
        return playerOneBoardEmpty() || playerTwoBoardEmpty();
    }

    private boolean playerOneBoardEmpty() {
        for (int i = 1; i <= 6; i++) {
            if (pitStones[i] > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean playerTwoBoardEmpty() {
        for (int i = 8; i <= 13; i++) {
            if (pitStones[i] > 0) {
                return false;
            }
        }
        return true;
    }

    public boolean handleMove(int move) {
        final boolean validMove = currentPlayer.isValidMove(move);
        if (!validMove || pitStones[move] < 1) {
            LOGGER.warn("invalid move={} for {}", move, currentPlayer);
            return false;
        }

        // do game logic
        return true;
    }

    /**
     * Perform a player's turn by moving the stones between pits
     * @param pit the pit selected by the user
     * @return whether the user's turn is ended
     */
    protected boolean moveStones(final int pit) {
        int pointer = pit;

        // return if pit has no stones
        if ( pitStones[pit] < 1 ) {
            return true;
        }

        // take stones out of pit
        int stones = pitStones[pit];
        pitStones[pit] = 0;

        while ( stones > 0 ) {
            ++pointer;

            // skip other player's storage pit and reset pointer
            if (pointer == 13) {
                pointer = 0;
            } else {
                pitStones[pointer]++;
                stones--;
            }

        }

        // set to point to the opposite pit
        int inversePointer = -pointer + 12;

        // Check for capture
        if (pointer < 6 && pitStones[pointer] == 1 && pitStones[inversePointer] > 0) {

            // Transfer this stone along with opposite pit's stones to store
            pitStones[6] += pitStones[inversePointer] + 1;

            // Clear the pits
            pitStones[pointer] = 0;
            pitStones[inversePointer] = 0;
        }

        // return true if the turn ended in storage pit
        return pointer == 6;
    }
}
