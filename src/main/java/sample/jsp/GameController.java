package sample.jsp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        GameData currentGameData = getGameData(request);
        handleMove(request.getSession(), move, currentGameData, model);
        return "game";
    }

    private GameData getGameData(HttpServletRequest request) {
        final HttpSession session = request.getSession(true);
        final Object attribute = session.getAttribute(GAME_DATA_SESSION_KEY);

        if (attribute == null) {
            LOGGER.info("starting new game");
            return new GameData();
        }
        return (GameData) attribute;
    }

    private void handleMove(HttpSession session, String move, GameData gameData, Map<String, Object> model) {
        if (StringUtils.isEmpty(move)) {
            return;
        }
        LOGGER.info("{} is valid move", move);

        final boolean validMove = gameData.handleMove(Integer.valueOf(move));
        if (validMove) {
            gameData.setNextPlayer();
        }
        session.setAttribute(GAME_DATA_SESSION_KEY, gameData);
        model.put(PIT_STONE_MODEL, gameData.getPitStones());
        model.put(CURRENT_PLAYER_MODEL, gameData.getCurrentPlayer());

        final GameResult result = gameData.checkForWin();
        if (result != GameResult.PLAYING) {
            LOGGER.info("{} wins", result);
            session.invalidate();
        }
    }

}
