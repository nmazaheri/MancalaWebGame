package sample.mancala;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import sample.mancala.model.Constants;
import sample.mancala.model.GameBoard;
import sample.mancala.model.Player;

public class GameStateTest {

	private GameState gameState;

	@BeforeEach
	void setUp() {
		gameState = new GameState();
	}

	@Test
	public void testPlayerTwoWins() {
		int[] testBoard = {4, 0, 0, 0, 0, 0, 0, 4, 1, 1, 0, 1, 0, 1};
		gameState.getGameBoard().setPitStones(testBoard);

		gameState.isGameOver();
		GameBoard gameBoard = gameState.getGameBoard();
		assertEquals(4, gameBoard.getScore(Player.ONE));
		assertEquals(8, gameBoard.getScore(Player.TWO));
		assertEquals(Constants.playerTwoWinMessage, gameState.getGameResult());
	}

	@Test
	public void testPlayerOneWins() {
		gameState.setNextPlayer();
		int[] testBoard = {4, 1, 0, 1, 0, 1, 0, 4, 0, 0, 0, 0, 0, 0};
		gameState.getGameBoard().setPitStones(testBoard);

		gameState.isGameOver();
		GameBoard gameBoard = gameState.getGameBoard();
		assertEquals(7, gameBoard.getScore(Player.ONE));
		assertEquals(4, gameBoard.getScore(Player.TWO));
		assertEquals(Constants.playerOneWinMessage, gameState.getGameResult());
	}

	@Test
	public void testPlayerTie() {
		int[] testBoard = {4, 0, 0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0};
		gameState.getGameBoard().setPitStones(testBoard);

		gameState.isGameOver();
		GameBoard gameBoard = gameState.getGameBoard();
		assertEquals(4, gameBoard.getScore(Player.ONE));
		assertEquals(4, gameBoard.getScore(Player.TWO));
		assertEquals(Constants.playerTieMessage, gameState.getGameResult());
	}

	@Test
	public void testPlayerOneOppositePit() {
		Player currentPlayer = gameState.getCurrentPlayer();
		int size = gameState.getGameBoard().getPitStones().length;
		assertEquals(13,
				gameState.getOppositePitLocation(1, currentPlayer.getScorePitLocation(), size));
		assertEquals(12,
				gameState.getOppositePitLocation(2, currentPlayer.getScorePitLocation(), size));
		assertEquals(11,
				gameState.getOppositePitLocation(3, currentPlayer.getScorePitLocation(), size));
		assertEquals(10,
				gameState.getOppositePitLocation(4, currentPlayer.getScorePitLocation(), size));
		assertEquals(9, gameState.getOppositePitLocation(5, currentPlayer.getScorePitLocation(), size));
		assertEquals(8, gameState.getOppositePitLocation(6, currentPlayer.getScorePitLocation(), size));
	}

	@Test
	public void testPlayerTwoOppositePit() {
		gameState.setNextPlayer();
		Player currentPlayer = gameState.getCurrentPlayer();
		int size = gameState.getGameBoard().getPitStones().length;
		assertEquals(1,
				gameState.getOppositePitLocation(13, currentPlayer.getScorePitLocation(), size));
		assertEquals(2,
				gameState.getOppositePitLocation(12, currentPlayer.getScorePitLocation(), size));
		assertEquals(3,
				gameState.getOppositePitLocation(11, currentPlayer.getScorePitLocation(), size));
		assertEquals(4,
				gameState.getOppositePitLocation(10, currentPlayer.getScorePitLocation(), size));
		assertEquals(5, gameState.getOppositePitLocation(9, currentPlayer.getScorePitLocation(), size));
		assertEquals(6, gameState.getOppositePitLocation(8, currentPlayer.getScorePitLocation(), size));
	}
}