package sample.mancala.game;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Contains data unique for each players
 */
public enum Player {
	PLAYER_ONE(Sets.newHashSet(1, 2, 3, 4, 5, 6), 0),
	PLAYER_TWO(Sets.newHashSet(8, 9, 10, 11, 12, 13), 7);

	private Set<Integer> regularPitLocations;
	private int scorePitLocation;

	Player(Set<Integer> regularPitLocations, int scorePitLocation) {
		this.regularPitLocations = regularPitLocations;
		this.scorePitLocation = scorePitLocation;
	}

	public boolean isValidRegularPitLocation(int pit) {
		return this.regularPitLocations.contains(pit);
	}

	public Set<Integer> getRegularPitLocations() {
		return regularPitLocations;
	}

	public int getScorePitLocation() {
		return scorePitLocation;
	}
}
