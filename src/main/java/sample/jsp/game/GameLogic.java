package sample.jsp.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.jsp.model.GameData;
import sample.jsp.model.GameStatus;
import sample.jsp.model.Player;

/**
 * Responsible for Mancala game logic
 */
public class GameLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLogic.class);
    private final static int TOP_OF_BOARD = 13;

    /**
     * Check if a player has won the game
     */
    public GameStatus checkForWin(GameData gameData) {
        final int[] pitStones = gameData.getPitStones();
        if (endGameCondition(pitStones)) {
            int playerOneScore = pitStones[0];
            int playerTwoScore = pitStones[7];
            return GameStatus.handleGameEnd(playerOneScore, playerTwoScore);
        }
        return GameStatus.PLAYING;
    }

    private boolean endGameCondition(final int[] pitStones) {
        return playerOneBoardEmpty(pitStones) || playerTwoBoardEmpty(pitStones);
    }

    private boolean playerOneBoardEmpty(final int[] pitStones) {
        for (int i = 1; i <= 6; i++) {
            if (pitStones[i] > 0) {
                return false;
            }
        }
        return true;
    }

    private boolean playerTwoBoardEmpty(final int[] pitStones) {
        for (int i = 8; i <= 13; i++) {
            if (pitStones[i] > 0) {
                return false;
            }
        }
        return true;
    }

    public GameData handleMove(int pos, GameData gameData) {
        final Player currentPlayer = gameData.getCurrentPlayer();
        final int[] pitStones = gameData.getPitStones();

        if (!currentPlayer.isValidMove(pos) || pitStones[pos] < 1) {
            LOGGER.warn("{} cannot move pos {}", currentPlayer, pos);
            return gameData;
        }
        return moveStones(pos, gameData);
    }

    /**
     * Perform a player's turn by moving the stones between pits
     *
     * @param pit the pit selected by the user
     * @return whether the user's turn is ended
     */
    protected GameData moveStones(int pit, GameData gameData) {
        final int[] pitStones = gameData.getPitStones();
        final Player currentPlayer = gameData.getCurrentPlayer();
        final Player nextPlayer = gameData.getNextPlayer();

        // take stones out of pit
        int stones = pitStones[pit];
        pitStones[pit] = 0;

        while (stones > 0) {
            pit--;

            if (pit == nextPlayer.getHome()) {
                continue;
            }
            if (pit < 0) {
                pit = TOP_OF_BOARD;
            }

            // distribute
            stones--;
            pitStones[pit]++;
        }

        // if you land in your own pit you get to go again
        if (pit == currentPlayer.getHome()) {
            return gameData;
        }

        // if you land in an empty board pit then you capture enemies stones
        if (pitStones[pit] == 1 && currentPlayer.isValidMove(pit)) {
            gameData.incrementCurrentPlayerScore();
            pitStones[pit] = 0;

            int diff = pit - currentPlayer.getHome();
            int oppositePit = getOppositePit(diff, currentPlayer);

            // take enemy stones
            gameData.incrementCurrentPlayerScore(pitStones[oppositePit]);
            pitStones[oppositePit] = 0;

        }
        gameData.setNextPlayer();
        return gameData;
    }

    private int getOppositePit(int diff, Player currentPlayer) {
        int potentialPit = currentPlayer.getHome() - diff;
        if (potentialPit < 0) {
            return TOP_OF_BOARD + 1 - diff;
        }
        return potentialPit;
    }
}
