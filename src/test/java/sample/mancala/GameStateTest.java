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


}