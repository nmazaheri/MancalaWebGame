package sample.mancala.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Responsible for Mancala game logic
 */
@Component
public class GameLogic {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameLogic.class);

	public GameState handleMove(int pos, GameState gameState) {
		final Player currentPlayer = gameState.getCurrentPlayer();
		final int[] pitStones = gameState.getPitStones();

		if (!currentPlayer.isValidRegularPitLocation(pos) || pitStones[pos] < 1) {
			LOGGER.info("{} cannot move position {}", currentPlayer, pos);
			return gameState;
		}
		return moveStones(pos, gameState);
	}

	/**
	 * Perform a player's turn by moving the stones between pits
	 *
	 * @param pit       the pit selected by the user
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

			if (pit == nextPlayer.getScorePitLocation()) {
				continue;
			}
			if (pit < 0) {
				pit = gameState.getPitStones().length - 1;
			}

			// distribute
			stones--;
			pitStones[pit]++;
		}

		// if you land in your own pit you get to go again
		if (pit == currentPlayer.getScorePitLocation()) {
			return gameState;
		}

		// if you land in one of your empty board pits then you capture enemies stones and your own
		if (pitStones[pit] == 1 && currentPlayer.isValidRegularPitLocation(pit)) {
			gameState.incrementCurrentPlayerScore();
			pitStones[pit] = 0;

			int oppositePit = getOppositePit(gameState.getPitStones().length,
					pit - currentPlayer.getScorePitLocation(), currentPlayer);

			// take enemy stones
			gameState.incrementCurrentPlayerScore(pitStones[oppositePit]);
			pitStones[oppositePit] = 0;
		}
		gameState.setNextPlayer();
		return gameState;
	}

	private int getOppositePit(int length, int diff, Player currentPlayer) {
		int potentialPit = currentPlayer.getScorePitLocation() - diff;
		if (potentialPit < 0) {
			return length - diff;
		}
		return potentialPit;
	}
}
