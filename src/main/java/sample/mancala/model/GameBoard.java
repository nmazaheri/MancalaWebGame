package sample.mancala.model;

import static sample.mancala.model.Player.ONE;
import static sample.mancala.model.Player.TWO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Defines the number of pits and the number of stones located in each pit
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

	private static String handleGameEnd(int playerOneScore, int playerTwoScore) {
		if (playerOneScore == playerTwoScore) {
			return Constants.playerTieMessage;
		}
		if (playerOneScore > playerTwoScore) {
			return Constants.playerOneWinMessage;
		}
		return Constants.playerTwoWinMessage;
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

	public int[] getPitStones() {
		return pitStones;
	}

	public boolean isNotEmpty(int pos) {
		return pitStones[pos] > 0;
	}

	public void increment(int pos, int val) {
		if (pos >= pitStones.length || pos < 0) {
			LOGGER.warn("invalid position = {}", pos);
			return;
		}
		pitStones[pos] += val;
	}

	public int getScore(Player player) {
		return pitStones[player.getScorePitLocation()];
	}

	public String getGameResult() {
		return handleGameEnd(getScore(ONE), getScore(TWO));
	}

	public int getOppositePitLocation(int currentPitLocation, Player player) {
		int scorePitLocation = player.getScorePitLocation();
		int diff = currentPitLocation - scorePitLocation;
		int result = scorePitLocation - diff;
		if (result < 0) {
			return pitStones.length - diff;
		}
		return result;
	}

}
