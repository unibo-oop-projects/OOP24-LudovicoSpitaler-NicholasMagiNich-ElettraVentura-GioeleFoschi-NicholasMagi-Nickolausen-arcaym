package arcaym.controller.game.impl;

import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.impl.AbstractController;
import arcaym.controller.game.api.ExtendedGameController;
import arcaym.model.game.core.engine.api.Game;
import arcaym.view.game.api.GameView;
import arcaym.view.game.impl.GameViewImpl;

/**
 * Implementation of {@link GameController} that extends
 * {@link AbstractController} of {@link GameView}.
 */
public class GameControllerImpl extends AbstractController<GameView> implements ExtendedGameController {
    private final Game game;

    /**
     * Basic contructor of GameControllerImpl.
     * 
     * @param switcher      that changes the Controller in charge
     * @param backOperation backOperation
     * @param game          game
     */
    public GameControllerImpl(final ControllerSwitcher switcher, final Runnable backOperation, final Game game) {
        super(switcher, backOperation);
        this.game = game;
        this.setView(new GameViewImpl(game));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startGame() {
        this.game.start(this.view());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Game getGame() {
        return this.game;
    }

}
