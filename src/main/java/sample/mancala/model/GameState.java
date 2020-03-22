package sample.mancala.model;

import static sample.mancala.model.Player.PLAYER_ONE;
import static sample.mancala.model.Player.PLAYER_TWO;

/**
 * Contains data needed to determine game state
 */
public class GameState {

	private final static int INITIAL_STONE_COUNT = 6;

	/**
	 * Defines the amount of stones in the pits
	 */
	private int[] pitStones;
	private Player currentPlayer;
	private GameStatus gameStatus;

	public GameState(int[] pitStones) {
		this.pitStones = pitStones;
		currentPlayer = PLAYER_ONE;
		gameStatus = GameStatus.PLAYING;
	}

	public GameState() {
		this.pitStones = new int[]{0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT};
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
		pitStones[getCurrentPlayer().getScorePit()] += val;
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
}
