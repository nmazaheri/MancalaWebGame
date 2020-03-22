package sample.mancala.controller;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.Arrays;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sample.mancala.GameState;
import sample.mancala.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameControllerTest {

	private static final int INITIAL_STONE_COUNT = 2;

	@Autowired
	private MockMvc mvc;

	private GameState gameState;


	@Before
	public void setUp() {
		int[] testPit = getTestPit();
		gameState = new GameState();
		gameState.getGameBoard().setPitStones(testPit);
	}

	private int[] getTestPit() {
		return new int[]{4, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 4, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT,
				INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT};
	}

	@Test
	public void testRenderGame() throws Exception {
		mvc.perform(get("/"))
				.andExpect(view().name(GameController.BOARD_VIEW))
				.andExpect(status().is2xxSuccessful())
				.andExpect(model().size(2))
				.andExpect(model()
						.attributeExists(GameController.CURRENT_PLAYER_MODEL, GameController.PIT_STONE_MODEL))
				.andExpect(model().attribute(GameController.CURRENT_PLAYER_MODEL, Player.ONE))
				.andExpect(
						model().attribute(GameController.PIT_STONE_MODEL, new GameState().getPitStones()));
	}

	@Test
	public void testEndGame() throws Exception {
//		if (GameStatus.PLAYING == gameStatus) {
//			mvc.perform(get("/")
//					.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
//					.andExpect(view().name(GameController.BOARD_VIEW))
//					.andExpect(status().is2xxSuccessful())
//					.andExpect(model().size(2));
//		} else {
//			mvc.perform(get("/")
//					.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
//					.andExpect(view().name(GameController.BOARD_VIEW))
//					.andExpect(status().is2xxSuccessful())
//					.andExpect(model().size(2))
//					.andExpect(
//							model().attribute(GameController.GAME_WINNER_MODEL, gameStatus.getMessage()));
//		}
	}


	@Test
	public void testNormalMove() throws Exception {
		assertEquals(Player.ONE, gameState.getCurrentPlayer());
		mvc.perform(get("/input/5")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		int[] expected = getTestPit();
		expected[3] = 3;
		expected[4] = 1;
		expected[5] = 0;
		assertArrayEquals(expected, gameState.getPitStones());
		assertEquals(Player.TWO, gameState.getCurrentPlayer());
	}

	@Test
	public void testLandInOwnScorePit() throws Exception {
		assertEquals(Player.ONE, gameState.getCurrentPlayer());
		mvc.perform(get("/input/2")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		int[] expected = getTestPit();
		expected[0] = 5;
		expected[1] = 3;
		expected[2] = 0;
		assertArrayEquals(expected, gameState.getPitStones());
		assertEquals(Player.ONE, gameState.getCurrentPlayer());
	}

	@Test
	public void testLandInOwnEmptyPit() throws Exception {
		assertEquals(Player.ONE, gameState.getCurrentPlayer());
		mvc.perform(get("/input/6")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));

		int[] expected = getTestPit();
		expected[0] = 7;
		expected[5] = 3;
		expected[6] = 0;
		expected[10] = 0;
		assertArrayEquals(expected, gameState.getPitStones());
		assertEquals(Player.TWO, gameState.getCurrentPlayer());

	}

	@Test
	public void testInvalidMovesForPlayerOne() throws Exception {
		for (int i = 7; i < 14; i++) {
			mvc.perform(get("/input/" + i)
					.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/"));

			assertUnchangedSessionFor(Player.ONE);
		}

		mvc.perform(get("/input/0")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		assertUnchangedSessionFor(Player.ONE);

		mvc.perform(get("/input/4")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		assertUnchangedSessionFor(Player.ONE);
	}

	private void assertUnchangedSessionFor(Player player) {
		assertArrayEquals(getTestPit(), gameState.getPitStones());
		assertEquals(player, gameState.getCurrentPlayer());
	}

	@Test
	public void testInvalidMovesForPlayerTwo() throws Exception {
		gameState.setNextPlayer();
		for (int i = 0; i < 8; i++) {
			mvc.perform(get("/input/" + i)
					.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
					.andExpect(status().is3xxRedirection())
					.andExpect(view().name("redirect:/"));

			assertUnchangedSessionFor(Player.TWO);
		}

		mvc.perform(get("/input/12")
				.sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
				.andExpect(status().is3xxRedirection())
				.andExpect(view().name("redirect:/"));
		assertUnchangedSessionFor(Player.TWO);
	}

}