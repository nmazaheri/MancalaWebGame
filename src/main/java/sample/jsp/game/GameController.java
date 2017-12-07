package sample.jsp.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sample.jsp.model.GameData;
import sample.jsp.model.GameStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    public static final String GAME_DATA_SESSION_KEY = "gameData";
    public static final String PIT_STONE_MODEL = "pitStones";
    public static final String CURRENT_PLAYER_MODEL = "currentPlayer";
    public static final String GAME_WINNER_MODEL = "gameWinner";

    @Autowired
    GameLogic gameLogic;

    @GetMapping("/")
    public String gameScreen(HttpServletRequest request, Map<String, Object> model) {
        final HttpSession session = request.getSession(true);
        final GameData gameData = getGameData(session);
        final GameStatus gameStatus = gameData.getGameStatus();

        model.put(PIT_STONE_MODEL, gameData.getPitStones());
        if (gameStatus != GameStatus.PLAYING) {
            LOGGER.info("{} wins", gameStatus);
            model.put(GAME_WINNER_MODEL, gameStatus.getMessage());
            session.invalidate();
        } else {
            model.put(CURRENT_PLAYER_MODEL, gameData.getCurrentPlayer());
        }

        return "game";
    }

    @GetMapping("/input/{move}")
    public String move(HttpServletRequest request, Map<String, Object> model, @PathVariable String move) {
        final HttpSession session = request.getSession(true);
        handleMove(session, move);
        return "redirect:/";
    }

    private GameData getGameData(HttpSession session) {
        final Object attribute = session.getAttribute(GAME_DATA_SESSION_KEY);

        if (attribute == null) {
            LOGGER.info("Starting new game");
            return new GameData();
        }
        return (GameData) attribute;
    }

    private void handleMove(HttpSession session, String move) {
        GameData gameData = getGameData(session);
        if (StringUtils.isEmpty(move)) {
            return;
        }

        int pos = Integer.valueOf(move);
        gameData = gameLogic.handleMove(pos, gameData);
        final GameStatus gameStatus = gameLogic.checkForWin(gameData);
        gameData.setGameStatus(gameStatus);
        session.setAttribute(GAME_DATA_SESSION_KEY, gameData);
    }

}
