package sample.mancala.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the amount of stones in the pits
 */
public class GameBoard {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameBoard.class);
	private final static int INITIAL_STONE_COUNT = 6;
	private int[] pitStones;

	public GameBoard() {
		this.pitStones = new int[]{0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT};
	}

	public void setPitStones(int[] pitStones) {
		this.pitStones = pitStones;
	}

	/**
	 * Check if a player has won the game
	 */
	public boolean isPlayerFinished(Player player) {
		return player.getRegularPitLocations().stream().mapToInt(playerRegularPit -> playerRegularPit)
				.noneMatch(regularPit -> pitStones[regularPit] > 0);
	}

	private void score(Player player, int value) {
		if (value <= 0) {
			return;
		}
		int scorePitLocation = player.getScorePitLocation();
		pitStones[scorePitLocation] += value;
	}

	public void scoreAllStones(Player player) {
		for (Integer playerRegularPitLocation : player.getRegularPitLocations()) {
			score(player, pitStones[playerRegularPitLocation]);
			pitStones[playerRegularPitLocation] = 0;
		}
	}

	public int getPlayerScore(Player player) {
		return pitStones[player.getScorePitLocation()];
	}

	int[] getPitStones() {
		return pitStones;
	}

	void increment(int pos, int val) {
		if (pos >= pitStones.length || pos < 0) {
			LOGGER.warn("invalid position = {}", pos);
			return;
		}
		pitStones[pos] += val;
	}


}