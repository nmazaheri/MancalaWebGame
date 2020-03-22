package sample.mancala;

import static sample.mancala.model.Player.ONE;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.mancala.model.GameBoard;
import sample.mancala.model.Player;

/**
 * Contains data needed to determine game state
 */
public class GameState {
	private static final Logger LOGGER = LoggerFactory.getLogger(GameState.class);

	private Player currentPlayer;
	private GameBoard gameBoard;

	public GameState() {
		gameBoard = new GameBoard();
		currentPlayer = ONE;
	}

	public int[] getPitStones() {
		return gameBoard.getPitStones();
	}

	public boolean isGameOver() {
		for (Player player: Player.values()) {
			if (!gameBoard.isPlayerFinished(player)) {
				continue;
			}
			gameBoard.scoreAllStones(player.getNextPlayer());
			return true;
		}
		return false;
	}

	public String getGameResult() {
		return gameBoard.getGameResult();
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
		this.currentPlayer = this.currentPlayer.getNextPlayer();
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public void handleMove(int pos) {
		final Player currentPlayer = getCurrentPlayer();
		final int[] pitStones = getPitStones();

		if (!currentPlayer.isValidRegularPitLocation(pos) || pitStones[pos] < 1) {
			LOGGER.info("{} cannot move position {}", currentPlayer, pos);
			return;
		}
		moveStones(pos);
	}

	/**
	 * Perform a player's turn by moving the stones between pits
	 *
	 * @param pit       the pit selected by the user
	 */
	protected void moveStones(int pit) {
		final int[] pitStones = getPitStones();
		final Player currentPlayer = getCurrentPlayer();
		final int enemyScorePitLocation = currentPlayer.getNextPlayer().getScorePitLocation();

		// take stones out of pit
		int stones = pitStones[pit];
		pitStones[pit] = 0;

		while (stones > 0) {
			pit--;

			if (pit == enemyScorePitLocation) {
				continue;
			}
			if (pit < 0) {
				pit = pitStones.length - 1;
			}

			// distribute
			stones--;
			pitStones[pit]++;
		}

		// if you land in your own pit you get to go again
		if (pit == currentPlayer.getScorePitLocation()) {
			return;
		}

		// if you land in one of your empty board pits then you capture enemies stones and your own
		if (pitStones[pit] == 1 && currentPlayer.isValidRegularPitLocation(pit)) {
			incrementCurrentPlayerScore();
			pitStones[pit] = 0;

			int oppositePit = getOppositePit(pitStones.length,
					pit - currentPlayer.getScorePitLocation(), currentPlayer);

			// take enemy stones
			incrementCurrentPlayerScore(pitStones[oppositePit]);
			pitStones[oppositePit] = 0;
		}
		setNextPlayer();
	}

	private int getOppositePit(int length, int diff, Player currentPlayer) {
		int potentialPit = currentPlayer.getScorePitLocation() - diff;
		if (potentialPit < 0) {
			return length - diff;
		}
		return potentialPit;
	}
}
