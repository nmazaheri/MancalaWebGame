package sample.jsp.model;

import java.util.Arrays;

import static sample.jsp.model.Player.PLAYER_ONE;
import static sample.jsp.model.Player.PLAYER_TWO;

/**
 *
 */
public class GameData {

    private final static int INITIAL_STONE_COUNT = 6;

    /**
     * Defines the amount of stones in the pits
     */
    private int[] pitStones;
    private Player currentPlayer;
    private GameStatus gameStatus;

    public GameData() {
        this.pitStones = new int[]{0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT};
        currentPlayer = PLAYER_ONE;
        gameStatus = GameStatus.PLAYING;
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

    public void incrementCurrentPlayerScore() {
        incrementCurrentPlayerScore(1);
    }

    public void incrementCurrentPlayerScore(int val) {
        pitStones[getCurrentPlayer().getHome()] += val;
    }

    public void setNextPlayer() {
        this.currentPlayer = getNextPlayer();
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    @Override
    public String toString() {
        return "GameData{" +
                "pitStones=" + Arrays.toString(pitStones) +
                ", currentPlayer=" + currentPlayer +
                '}';
    }
}
