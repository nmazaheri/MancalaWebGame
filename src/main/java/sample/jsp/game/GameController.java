package sample.jsp.game;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sample.jsp.model.GameResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class GameController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GameController.class);
    public static final String GAME_DATA_SESSION_KEY = "gameData";
    public static final String PIT_STONE_MODEL = "pitStones";
    public static final String CURRENT_PLAYER_MODEL = "currentPlayer";

    @GetMapping("/")
    public String welcome(HttpServletRequest request, @RequestParam(required = false) String move, Map<String, Object> model) {
        final GameData gameData = handleMove(request, move);
        model.put(PIT_STONE_MODEL, gameData.getPitStones());
        model.put(CURRENT_PLAYER_MODEL, gameData.getCurrentPlayer());
        return "game";
    }

    private GameData getGameData(HttpServletRequest request, HttpSession session) {
        final Object attribute = session.getAttribute(GAME_DATA_SESSION_KEY);

        if (attribute == null) {
            LOGGER.info("Starting new game");
            return new GameData();
        }
        return (GameData) attribute;
    }

    private GameData handleMove(HttpServletRequest request, String move) {
        final HttpSession session = request.getSession(true);
        GameData gameData = getGameData(request, session);
        if (StringUtils.isEmpty(move)) {
            LOGGER.warn("no move provided");
            return gameData;
        }

        final boolean validMove = gameData.handleMove(Integer.valueOf(move));
        if (validMove) {
            gameData.setNextPlayer();
        }

        session.setAttribute(GAME_DATA_SESSION_KEY, gameData);

        final GameResult result = gameData.checkForWin();
        if (result != GameResult.PLAYING) {
            LOGGER.info("{} wins", result);
            session.invalidate();
        }
        return gameData;
    }

}
