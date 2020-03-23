package sample.mancala.controller;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sample.mancala.GameState;
import sample.mancala.model.Constants;

/**
 * Servlet used to handle user input and render game board
 */
@Controller
public class GameController {

	private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);

	@GetMapping("/")
	public String renderGame(HttpServletRequest request, Map<String, Object> model) {
		final HttpSession session = request.getSession(true);
		final GameState gameState = getGameData(session);

		if (gameState.isGameOver()) {
			String winResult = gameState.getGameBoard().getGameResult();
			LOGGER.info("{} wins", winResult);
			model.put(Constants.GAME_MESSAGE_MODEL, winResult);
			session.invalidate();
		}
		model.put(Constants.PIT_STONE_MODEL, gameState
				.getGameBoard().getPitStones());
		model.put(Constants.CURRENT_PLAYER_MODEL, gameState.getCurrentPlayer());
		return Constants.BOARD_VIEW;
	}

	@GetMapping("/input/{move}")
	public String handleUserMove(HttpServletRequest request, @PathVariable String move) {
		final HttpSession session = request.getSession(true);
		GameState gameState = getGameData(session);
		gameState.handleMove(Integer.parseInt(move));
		session.setAttribute(Constants.GAME_DATA_SESSION_KEY, gameState);
		return "redirect:/";
	}

	private GameState getGameData(HttpSession session) {
		final Object attribute = session.getAttribute(Constants.GAME_DATA_SESSION_KEY);
		if (attribute == null) {
			LOGGER.debug("Starting new game");
			return new GameState();
		}
		return (GameState) attribute;
	}

}
