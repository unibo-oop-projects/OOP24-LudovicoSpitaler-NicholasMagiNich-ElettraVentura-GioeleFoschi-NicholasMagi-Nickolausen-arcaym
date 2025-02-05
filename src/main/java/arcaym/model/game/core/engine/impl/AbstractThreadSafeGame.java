package arcaym.model.game.core.engine.impl;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.engine.api.GameObserver;
import arcaym.model.game.core.engine.api.GameState;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsManager;
import arcaym.model.game.core.events.impl.ThreadSafeEventsManager;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.model.user.impl.UserStateImpl;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * Abstract implementation of {@link Game}.
 * It provides events management while leaving the update logic.
 */
public abstract class AbstractThreadSafeGame implements Game {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractThreadSafeGame.class);

    private final EventsManager<GameEvent> gameEventsManager = new ThreadSafeEventsManager<>();
    private final EventsManager<InputEvent> inputEventsManager = new ThreadSafeEventsManager<>();
    private final GameState gameState;
    private final GameScene gameScene;

    /**
     * Initialize with the given scene.
     * 
     * @param gameScene game scene
     * @param boundaries total level boundaries
     */
    protected AbstractThreadSafeGame(final GameScene gameScene, final Rectangle boundaries) {
        this.gameScene = Objects.requireNonNull(gameScene);
        this.gameState = new DefaultGameState(Objects.requireNonNull(boundaries));
    }

    /**
     * Get the game events manager.
     * 
     * @return game events manager
     */
    protected final EventsManager<GameEvent> gameEventsManager() {
        return this.gameEventsManager;
    }

    /**
     * Get the input events manager.
     * 
     * @return input events manager
     */
    protected final EventsManager<InputEvent> inputEventsManager() {
        return this.inputEventsManager;
    }

    /**
     * Get the game scene.
     * 
     * @return game scene
     */
    protected final GameScene scene() {
        return this.gameScene;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressFBWarnings(
        value = {
            "EI_EXPOSE_REP"
        },
        justification = "GameStateInfo is a read-only view on the game state "
        + "with only read-only views as its fields"
    )
    public GameStateInfo state() {
        return this.gameState;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final InputEvent event) {
        this.inputEventsManager.scheduleEvent(event);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final GameObserver observer) {
        LOGGER.info("Setting up game view");
        Objects.requireNonNull(observer);
        observer.setInputEventsScheduler(this.inputEventsManager);
        observer.registerEventsCallbacks(this.gameEventsManager, this.gameState);
        new UserStateImpl().registerEventsCallbacks(this.gameEventsManager, this.gameState);
        LOGGER.info("Setting up game scene");
        this.gameScene.consumePendingActions(observer);
        this.gameScene.getGameObjects().forEach(
            o -> o.setup(this.gameEventsManager, this.inputEventsManager, gameScene, this.gameState)
        );
        LOGGER.info("Setting up game state");
        this.gameState.setupCallbacks(this.gameEventsManager);
        LOGGER.info("Registering stop conditions");
        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, e -> this.scheduleStop());
        this.gameEventsManager.registerCallback(GameEvent.VICTORY, e -> this.scheduleStop());
    }

}
