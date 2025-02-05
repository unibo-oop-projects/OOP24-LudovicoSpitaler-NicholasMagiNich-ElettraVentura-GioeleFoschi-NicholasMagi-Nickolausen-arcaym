package arcaym.controller.game.impl;

import java.util.Objects;

import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.impl.AbstractController;
import arcaym.controller.game.api.ExtendedGameController;
import arcaym.controller.game.api.GameController;
import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameController} that extends
 * {@link AbstractController} of {@link GameView}.
 */
public class GameControllerImpl extends AbstractController<GameView> implements ExtendedGameController {
    private final Game game;

    /**
     * Basic contructor of GameControllerImpl.
     * 
     * @param game game
     * @param switcher that changes the Controller in charge
     */
    public GameControllerImpl(final Game game, final ControllerSwitcher switcher) {
        super(switcher);
        this.game = game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameStateInfo getGameState() {
        return this.game.state();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setView(final GameView view) {
        super.setView(view);
        this.game.start(this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() {
        this.game.scheduleStop();
        super.close();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(final EventsScheduler<InputEvent> eventsScheduler) {
        this.view().setInputEventsScheduler(Objects.requireNonNull(eventsScheduler));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject, final int zIndex) {
        this.view().createObject(Objects.requireNonNull(gameObject), zIndex);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        this.view().updateObject(Objects.requireNonNull(gameObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyObject(final GameObjectInfo gameObject) {
        this.view().destroyObject(Objects.requireNonNull(gameObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber, final GameStateInfo gameState) {
        this.view().registerEventsCallbacks(Objects.requireNonNull(eventsSubscriber), Objects.requireNonNull(gameState));
    }

}
