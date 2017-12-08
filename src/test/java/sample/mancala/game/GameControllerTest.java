package sample.mancala.game;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import sample.mancala.model.GameState;
import sample.mancala.model.GameStatus;
import sample.mancala.model.Player;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(GameController.class)
public class GameControllerTest {

    private final int INITIAL_STONE_COUNT = 2;

    @Autowired
    private MockMvc mvc;

    private GameState gameState;
    private int[] testPit;


    @Before
    public void setUp() throws Exception {
        testPit = createTestPit();
        gameState = new GameState(testPit);
    }

    private int[] createTestPit() {
        return new int[]{4, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 4, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, INITIAL_STONE_COUNT, 0, INITIAL_STONE_COUNT};
    }

    @Test
    public void testRenderGame() throws Exception {
        mvc.perform(get("/"))
                .andExpect(view().name("game"))
                .andExpect(status().is2xxSuccessful())
                .andExpect(model().size(2))
                .andExpect(model().attributeExists(GameController.CURRENT_PLAYER_MODEL, GameController.PIT_STONE_MODEL))
                .andExpect(model().attribute(GameController.CURRENT_PLAYER_MODEL, Player.PLAYER_ONE))
                .andExpect(model().attribute(GameController.PIT_STONE_MODEL, new GameState().getPitStones()));
    }

    @Test
    public void testEndGame() throws Exception {
        for (GameStatus gameStatus : GameStatus.values()) {
            gameState.setGameStatus(gameStatus);
            if (GameStatus.PLAYING == gameStatus) {
                mvc.perform(get("/")
                        .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                        .andExpect(view().name("game"))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(model().size(2));
            } else {
                mvc.perform(get("/")
                        .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                        .andExpect(view().name("game"))
                        .andExpect(status().is2xxSuccessful())
                        .andExpect(model().size(2))
                        .andExpect(model().attribute(GameController.GAME_WINNER_MODEL, gameStatus.getMessage()));
            }
        }
    }

    @Test
    public void testNormalMove() throws Exception {
        assertEquals(Player.PLAYER_ONE, gameState.getCurrentPlayer());
        mvc.perform(get("/input/5")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        int[] expected = createTestPit();
        expected[3] = 3;
        expected[4] = 1;
        expected[5] = 0;
        assertTrue(Arrays.equals(expected, gameState.getPitStones()));
        assertEquals(Player.PLAYER_TWO, gameState.getCurrentPlayer());
        assertEquals(GameStatus.PLAYING, gameState.getGameStatus());
    }

    @Test
    public void testLandInOwnScorePit() throws Exception {
        assertEquals(Player.PLAYER_ONE, gameState.getCurrentPlayer());
        mvc.perform(get("/input/2")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        int[] expected = createTestPit();
        expected[0] = 5;
        expected[1] = 3;
        expected[2] = 0;
        assertTrue(Arrays.equals(expected, gameState.getPitStones()));
        assertEquals(Player.PLAYER_ONE, gameState.getCurrentPlayer());
        assertEquals(GameStatus.PLAYING, gameState.getGameStatus());
    }

    @Test
    public void testLandInOwnEmptyPit() throws Exception {
        assertEquals(Player.PLAYER_ONE, gameState.getCurrentPlayer());
        mvc.perform(get("/input/6")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));

        int[] expected = createTestPit();
        expected[0] = 7;
        expected[5] = 3;
        expected[6] = 0;
        expected[10] = 0;
        assertTrue(Arrays.equals(expected, gameState.getPitStones()));
        assertEquals(Player.PLAYER_TWO, gameState.getCurrentPlayer());
        assertEquals(GameStatus.PLAYING, gameState.getGameStatus());

    }

    @Test
    public void testInvalidMovesForPlayerOne() throws Exception {
        for (int i = 7; i < 14; i++) {
            mvc.perform(get("/input/" + i)
                    .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                    .andExpect(status().is3xxRedirection())
                    .andExpect(view().name("redirect:/"));

            assertUnchangedSessionFor(Player.PLAYER_ONE);
        }

        mvc.perform(get("/input/0")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        assertUnchangedSessionFor(Player.PLAYER_ONE);

        mvc.perform(get("/input/4")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        assertUnchangedSessionFor(Player.PLAYER_ONE);
    }

    private void assertUnchangedSessionFor(Player player) {
        assertTrue(Arrays.equals(createTestPit(), gameState.getPitStones()));
        assertEquals(GameStatus.PLAYING, gameState.getGameStatus());
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

            assertUnchangedSessionFor(Player.PLAYER_TWO);
        }

        mvc.perform(get("/input/12")
                .sessionAttr(GameController.GAME_DATA_SESSION_KEY, gameState))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/"));
        assertUnchangedSessionFor(Player.PLAYER_TWO);
    }

}