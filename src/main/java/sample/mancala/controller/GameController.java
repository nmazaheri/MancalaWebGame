package sample.mancala.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sample.mancala.game.GameLogic;
import sample.mancala.game.GameState;
import sample.mancala.game.GameStatus;

/**
 * Servlet used to handle user input and render game
 */
@Controller
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	public static final String BOARD_VIEW = "board";

	static final String GAME_DATA_SESSION_KEY = "gameData";
	static final String PIT_STONE_MODEL = "pitStones";
	static final String CURRENT_PLAYER_MODEL = "currentPlayer";
	static final String GAME_WINNER_MODEL = "gameWinner";
	private final GameLogic gameLogic;

	public GameController(GameLogic gameLogic) {
		this.gameLogic = gameLogic;
	}

	@GetMapping("/")
	public String renderGame(HttpServletRequest request, Map<String, Object> model) {
		final HttpSession session = request.getSession(true);
		final GameState gameState = getGameData(session);
		final GameStatus gameStatus = gameState.getGameStatus();

		model.put(PIT_STONE_MODEL, gameState.getPitStones());
		if (gameStatus != GameStatus.PLAYING) {
			LOGGER.info("{} wins", gameStatus);
			model.put(GAME_WINNER_MODEL, gameStatus.getMessage());
			session.invalidate();
		} else {
			model.put(CURRENT_PLAYER_MODEL, gameState.getCurrentPlayer());
		}
		return BOARD_VIEW;
	}

	@GetMapping("/input/{move}")
	public String handleUserMove(HttpServletRequest request, @PathVariable String move) {
		final HttpSession session = request.getSession(true);
		handleMove(session, move);
		return "redirect:/";
	}

	private void handleMove(HttpSession session, String move) {
		GameState gameState = getGameData(session);
		int pos = Integer.valueOf(move);
		gameState = gameLogic.handleMove(pos, gameState);
		final GameStatus gameStatus = gameLogic.checkForWin(gameState);
		gameState.setGameStatus(gameStatus);
		session.setAttribute(GAME_DATA_SESSION_KEY, gameState);
	}

	private GameState getGameData(HttpSession session) {
		final Object attribute = session.getAttribute(GAME_DATA_SESSION_KEY);
		if (attribute == null) {
			LOGGER.debug("Starting new game");
			return new GameState();
		}
		return (GameState) attribute;
	}

}
