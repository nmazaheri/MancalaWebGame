package sample.mancala.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.mancala.model.GameState;
import sample.mancala.model.GameStatus;
import sample.mancala.model.Player;

/**
 * Responsible for Mancala game logic
 */
public class GameLogic {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameLogic.class);
    private final static int TOP_OF_BOARD = 13;

    /**
     * Check if a player has won the game
     */
    public GameStatus checkForWin(GameState gameState) {
        final int[] pitStones = gameState.getPitStones();
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

    public GameState handleMove(int pos, GameState gameState) {
        final Player currentPlayer = gameState.getCurrentPlayer();
        final int[] pitStones = gameState.getPitStones();

        if (!currentPlayer.isYourPit(pos) || pitStones[pos] < 1) {
            LOGGER.warn("{} cannot move pos {}", currentPlayer, pos);
            return gameState;
        }
        return moveStones(pos, gameState);
    }

    /**
     * Perform a player's turn by moving the stones between pits
     *
     * @param pit the pit selected by the user
     * @param gameState contains gameboard and current player
     * @return whether the user's turn is ended
     */
    protected GameState moveStones(int pit, GameState gameState) {
        final int[] pitStones = gameState.getPitStones();
        final Player currentPlayer = gameState.getCurrentPlayer();
        final Player nextPlayer = gameState.getNextPlayer();

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
            return gameState;
        }

        // if you land in one of your empty board pits then you capture enemies stones and your own
        if (pitStones[pit] == 1 && currentPlayer.isYourPit(pit)) {
            gameState.incrementCurrentPlayerScore();
            pitStones[pit] = 0;

            int oppositePit = getOppositePit(pit - currentPlayer.getHome(), currentPlayer);

            // take enemy stones
            gameState.incrementCurrentPlayerScore(pitStones[oppositePit]);
            pitStones[oppositePit] = 0;
        }
        gameState.setNextPlayer();
        return gameState;
    }

    private int getOppositePit(int diff, Player currentPlayer) {
        int potentialPit = currentPlayer.getHome() - diff;
        if (potentialPit < 0) {
            return TOP_OF_BOARD + 1 - diff;
        }
        return potentialPit;
    }
}
