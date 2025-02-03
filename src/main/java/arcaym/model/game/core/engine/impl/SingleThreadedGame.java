package arcaym.model.game.core.engine.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.impl.Rectangle;
import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame extends AbstractThreadSafeGame {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";
    private static final long GAME_LOOP_PERIOD_MS = 10;
    private static final long SECOND_MS = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadedGame.class);
    private Optional<Thread> gameLoopThread = Optional.empty();
    private volatile boolean runGameLoop;

    /**
     * Initialize with the given scene.
     * 
     * @param gameScene game scene
     * @param boundaries total level boundaries
     */
    public SingleThreadedGame(final GameScene gameScene, final Rectangle boundaries) {
        super(gameScene, boundaries);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final GameView gameView) {
        super.start(gameView);
        this.runGameLoop = true;
        LOGGER.info("Starting background thread");
        this.gameLoopThread = Optional.of(
            Thread.ofPlatform()
                .name(GAME_LOOP_THREAD_NAME)
                .daemon()
                .start(() -> this.gameLoop(gameView))
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleStop() {
        if (this.gameLoopThread.isEmpty()) {
            throw new IllegalStateException("Game is not running");
        }
        this.runGameLoop = false;
        LOGGER.info("Requested game loop stop");
    }

    private void gameLoop(final GameView gameView) {
        LOGGER.info("Game loop thread started");
        long deltaTime = this.updateDeltaTime(0);
        while (this.runGameLoop) {
            this.inputEventsManager().consumePendingEvents();
            deltaTime = this.updateDeltaTime(deltaTime);
            // no stream beacause delta time is not final
            for (final var gameObject : this.scene().getGameObjects()) {
                gameObject.update(deltaTime, this.gameEventsManager(), this.scene(), this.state());
            }
            this.scene().getGameObjects().forEach(gameView::updateObject);
            this.scene().consumePendingActions(gameView);
            this.gameEventsManager().consumePendingEvents();
        }
        LOGGER.info("Finished game loop");
    }

    private long updateDeltaTime(final long start) {
        final var deltaTime = System.currentTimeMillis() - start;
        if (deltaTime < GAME_LOOP_PERIOD_MS) {
            try {
                Thread.sleep(GAME_LOOP_PERIOD_MS - deltaTime);
            } catch (InterruptedException e) {
                LOGGER.warn("Update frame waiting interrupted");
            }
        }
        LOGGER.info(
            new StringBuilder("FPS: ")
                .append(SECOND_MS / deltaTime)
                .append(", Delta: ")
                .append(deltaTime)
                .toString()
        );
        return deltaTime;
    }

}
