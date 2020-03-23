package sample.mancala.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class GameBoardTest {

	private GameBoard gameBoard = new GameBoard();

	@Test
	public void testPlayerOneOppositePit() {
		Player currentPlayer = Player.ONE;
		assertEquals(13, gameBoard.getOppositePitLocation(1, currentPlayer));
		assertEquals(12, gameBoard.getOppositePitLocation(2, currentPlayer));
		assertEquals(11, gameBoard.getOppositePitLocation(3, currentPlayer));
		assertEquals(10, gameBoard.getOppositePitLocation(4, currentPlayer));
		assertEquals(9, gameBoard.getOppositePitLocation(5, currentPlayer));
		assertEquals(8, gameBoard.getOppositePitLocation(6, currentPlayer));
	}

	@Test
	public void testPlayerTwoOppositePit() {
		Player currentPlayer = Player.TWO;
		assertEquals(1, gameBoard.getOppositePitLocation(13, currentPlayer));
		assertEquals(2, gameBoard.getOppositePitLocation(12, currentPlayer));
		assertEquals(3, gameBoard.getOppositePitLocation(11, currentPlayer));
		assertEquals(4, gameBoard.getOppositePitLocation(10, currentPlayer));
		assertEquals(5, gameBoard.getOppositePitLocation(9, currentPlayer));
		assertEquals(6, gameBoard.getOppositePitLocation(8, currentPlayer));
	}

}