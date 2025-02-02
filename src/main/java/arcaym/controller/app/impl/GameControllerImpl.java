package arcaym.controller.app.impl;

import arcaym.controller.app.api.GameController;
import arcaym.model.game.core.engine.api.Game;

public class GameControllerImpl implements GameController {
    private final Game game;

    public GameControllerImpl(final Game game) {
        this.game = game;
    }
}
