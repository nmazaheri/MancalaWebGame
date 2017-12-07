package sample.jsp.model;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
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

    public boolean isValidMove(int move) {
        return validMoves.contains(move);
    }

    public int getHome() {
        return home;
    }
}
