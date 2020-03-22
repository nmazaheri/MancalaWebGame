package sample.mancala.game;

import static sample.mancala.game.Player.PLAYER_ONE;
import static sample.mancala.game.Player.PLAYER_TWO;

/**
 * Contains data needed to determine game state
 */
public class GameState {

	private Player currentPlayer;
	private GameStatus gameStatus;
	private GameBoard gameBoard;

	public GameState() {
		gameBoard = new GameBoard();
		currentPlayer = PLAYER_ONE;
		gameStatus = GameStatus.PLAYING;
	}

	public int[] getPitStones() {
		return gameBoard.getPitStones();
	}

	public Player getNextPlayer() {
		if (currentPlayer.equals(PLAYER_ONE)) {
			return PLAYER_TWO;
		}
		return PLAYER_ONE;
	}

	public void handleGameOver() {
		if (!gameBoard.isPlayerFinished(currentPlayer)) {
			return;
		}
		gameBoard.scoreAllStones(getNextPlayer());
		int playerOneScore = gameBoard.getPlayerScore(PLAYER_ONE);
		int playerTwoScore = gameBoard.getPlayerScore(PLAYER_TWO);
		gameStatus = GameStatus.handleGameEnd(playerOneScore, playerTwoScore);
	}

	public void incrementCurrentPlayerScore() {
		incrementCurrentPlayerScore(1);
	}

	public void incrementCurrentPlayerScore(int val) {
		gameBoard.increment(currentPlayer.getScorePitLocation(), val);
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setNextPlayer() {
		this.currentPlayer = getNextPlayer();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public GameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameStatus(GameStatus gameStatus) {
		this.gameStatus = gameStatus;
	}
}
