package sample.jsp.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.jsp.model.GameResult;
import sample.jsp.model.Player;

import java.util.Arrays;

import static sample.jsp.model.Player.PLAYER_ONE;
import static sample.jsp.model.Player.PLAYER_TWO;

/**
 *
 */
public class GameData {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameData.class);
    private final static int INITIAL_STONE_COUNT = 6;
    private final static int TOP_OF_BOARD = 13;

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

    public Player getNextPlayer() {
        if (currentPlayer.equals(PLAYER_ONE)) {
            return PLAYER_TWO;
        } else {
            return PLAYER_ONE;
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

    public void handleMove(int pos) {
        final boolean validMove = currentPlayer.isValidMove(pos);
        if (!validMove || pitStones[pos] < 1) {
            LOGGER.warn("{} cannot use pos {}", currentPlayer, pos);
            return;
        }

        final boolean turnEnded = moveStones(pos);
        if (turnEnded) {
            currentPlayer = getNextPlayer();
        }
        return;
    }

    /**
     * Perform a player's turn by moving the stones between pits
     *
     * @param pit the pit selected by the user
     * @return whether the user's turn is ended
     */
    protected boolean moveStones(int pit) {

        // take stones out of pit
        int stones = pitStones[pit];
        pitStones[pit] = 0;

        while (stones > 0) {
            pit--;

            if (pit == getNextPlayer().getHome()) {
                continue;
            }
            if (pit < 0) {
                pit = TOP_OF_BOARD;
            }

            // distribute
            pitStones[pit]++;
            stones--;
        }

        // if you land in your own pit you get to go again
        if (pit == getCurrentPlayer().getHome()) {
            return false;
        }

        // if you land in an empty board pit then you capture enemies stones
        if (pitStones[pit] == 1 && getCurrentPlayer().isValidMove(pit)) {
            incrementCurrentPlayerScore();
            pitStones[pit] = 0;

            int diff = pit - getCurrentPlayer().getHome();
            int oppositePit = getOppositePit(diff);

            // take enemy stones
            incrementCurrentPlayerScore(pitStones[oppositePit]);
            pitStones[oppositePit] = 0;

        }
        return true;
    }

    private int getOppositePit(int diff) {
        int potentialPit = getCurrentPlayer().getHome() - diff;
        if (potentialPit < 0) {
            return TOP_OF_BOARD + 1 - diff;
        }
        return potentialPit;
    }

    private void incrementCurrentPlayerScore() {
        incrementCurrentPlayerScore(1);
    }

    private void incrementCurrentPlayerScore(int val) {
        pitStones[getCurrentPlayer().getHome()] += val;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "pitStones=" + Arrays.toString(pitStones) +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
}
