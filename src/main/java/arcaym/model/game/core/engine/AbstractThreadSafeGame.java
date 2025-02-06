package arcaym.model.game.core.engine;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.Rectangle;
import arcaym.controller.game.GameUser;
import arcaym.model.game.core.events.EventsManager;
import arcaym.model.game.core.events.ThreadSafeEventsManager;
import arcaym.model.game.core.scene.GameScene;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.events.InputEvent;
import arcaym.model.user.UserStateManagerImpl;
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
    private boolean started;

    /**
     * Initialize with the given scene and boundaries.
     * 
     * @param gameScene game scene
     * @param boundaries total level boundaries
     */
    protected AbstractThreadSafeGame(final GameScene gameScene, final Rectangle boundaries) {
        this.gameScene = Objects.requireNonNull(gameScene);
        this.gameState = new DefaultGameState(Objects.requireNonNull(boundaries));
    }

    /**
     * @return game events manager
     */
    protected final EventsManager<GameEvent> gameEventsManager() {
        return this.gameEventsManager;
    }

    /**
     * @return input events manager
     */
    protected final EventsManager<InputEvent> inputEventsManager() {
        return this.inputEventsManager;
    }

    /**
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
    public void start(final GameUser user) {
        Objects.requireNonNull(user);
        if (this.started) {
            return;
        }
        LOGGER.info("Enabling events managers");
        this.inputEventsManager.enable();
        this.gameEventsManager.enable();
        LOGGER.info("Setting up game view");
        user.setInputEventsScheduler(this.inputEventsManager);
        user.registerEventsCallbacks(this.gameEventsManager, this.gameState);
        new UserStateManagerImpl().registerEventsCallbacks(this.gameEventsManager, this.gameState);
        LOGGER.info("Setting up game scene");
        this.gameScene.consumePendingEvents(user);
        this.gameScene.getGameObjects().forEach(
            o -> o.setup(this.gameEventsManager, this.inputEventsManager, gameScene, this.gameState)
        );
        LOGGER.info("Setting up game state");
        this.gameState.setupCallbacks(this.gameEventsManager);
        LOGGER.info("Registering stop conditions");
        this.gameEventsManager.registerCallback(GameEvent.GAME_OVER, e -> this.scheduleStop());
        this.gameEventsManager.registerCallback(GameEvent.VICTORY, e -> this.scheduleStop());
        this.started = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleStop() {
        if (!this.started) {
            return;
        }
        LOGGER.info("Disabling events managers");
        this.inputEventsManager.disable();
        this.gameEventsManager.disable();
        this.started = false;
    }

}
