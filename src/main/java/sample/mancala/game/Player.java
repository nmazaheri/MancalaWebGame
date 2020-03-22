package sample.mancala.game;

import com.google.common.collect.Sets;
import java.util.Set;

/**
 * Contains data unique for each players
 */
public enum Player {
	PLAYER_ONE(Sets.newHashSet(1, 2, 3, 4, 5, 6), 0),
	PLAYER_TWO(Sets.newHashSet(8, 9, 10, 11, 12, 13), 7);

	private Set<Integer> regularPit;
	private int scorePit;

	Player(Set<Integer> regularPit, int scorePit) {
		this.regularPit = regularPit;
		this.scorePit = scorePit;
	}

	public boolean isYourRegularPit(int pit) {
		return this.regularPit.contains(pit);
	}

	public int getScorePit() {
		return scorePit;
	}
}
