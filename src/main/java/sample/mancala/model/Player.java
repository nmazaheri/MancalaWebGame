package sample.mancala.model;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 * Contains data unique for each players
 */
public enum Player {
    PLAYER_ONE(Sets.newHashSet(1, 2, 3, 4, 5, 6), 0),
    PLAYER_TWO(Sets.newHashSet(8, 9, 10, 11, 12, 13), 7);

    private Set<Integer> validMoves;
    private int home;

    Player(Set<Integer> validMoves, int home) {
        this.validMoves = validMoves;
        this.home = home;
    }

    public boolean isYourPit(int pit) {
        return validMoves.contains(pit);
    }

    public int getHome() {
        return home;
    }
}
