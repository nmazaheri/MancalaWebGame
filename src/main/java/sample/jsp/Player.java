package sample.jsp;

import com.google.common.collect.Sets;

import java.util.Set;

/**
 *
 */
public enum Player {
    PLAYER_ONE(Sets.newHashSet(1, 2, 3, 4, 5, 6)),
    PLAYER_TWO(Sets.newHashSet(8, 9, 10, 11, 12, 13));

    private Set<Integer> validMoves;

    Player(Set<Integer> validMoves) {
        this.validMoves = validMoves;
    }

    public boolean isValidMove(int move) {
        return validMoves.contains(move);
    }

}
