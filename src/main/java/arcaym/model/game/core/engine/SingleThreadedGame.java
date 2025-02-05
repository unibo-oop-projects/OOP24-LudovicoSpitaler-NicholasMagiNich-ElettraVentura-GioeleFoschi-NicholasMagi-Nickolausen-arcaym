package arcaym.model.game.core.engine;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import arcaym.common.geometry.Rectangle;
import arcaym.controller.game.GameObserver;
import arcaym.model.game.core.scene.GameScene;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame extends AbstractThreadSafeGame {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";
    private static final long DESIRED_FPS = 60;
    private static final long SECOND_MS = 1000;
    private static final Logger LOGGER = LoggerFactory.getLogger(SingleThreadedGame.class);
    private volatile boolean runGameLoop;

    /**
     * Initialize with the given scene and boundaries.
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
    public void start(final GameObserver observer) {
        super.start(observer);
        if (!this.runGameLoop) {
            this.runGameLoop = true;
            LOGGER.info("Starting background thread");
            Thread.ofPlatform()
                .name(GAME_LOOP_THREAD_NAME)
                .daemon()
                .start(() -> this.gameLoop(observer));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleStop() {
        if (this.runGameLoop) {
            this.runGameLoop = false;
            LOGGER.info("Requested game loop stop");
        }
        super.scheduleStop();
    }

    private void gameLoop(final GameObserver observer) {
        LOGGER.info("Game loop thread started");
        long deltaTime = 0;
        long lastFrameTime = System.currentTimeMillis();
        while (this.runGameLoop) {
            this.inputEventsManager().consumePendingEvents();
            for (final var gameObject : this.scene().getGameObjects()) {
                gameObject.update(deltaTime, this.gameEventsManager(), this.scene(), this.state());
            }
            this.scene().getGameObjects().forEach(observer::updateObject);
            this.scene().consumePendingActions(observer);
            this.gameEventsManager().consumePendingEvents();
            deltaTime = this.updateDeltaTime(lastFrameTime);
            lastFrameTime = System.currentTimeMillis();
        }
        LOGGER.info("Finished game loop");
        this.runGameLoop = false;
    }

    private long updateDeltaTime(final long lastFrameTime) {
        var deltaTime = this.calculateDeltaTime(lastFrameTime);
        final var period = SECOND_MS / DESIRED_FPS;
        if (deltaTime < period) {
            try {
                Thread.sleep(period - deltaTime);
            } catch (InterruptedException e) {
                LOGGER.warn("Update frame waiting interrupted");
            }
        }
        deltaTime = this.calculateDeltaTime(lastFrameTime);
        LOGGER.info(
            new StringBuilder("FPS: ")
            .append(SECOND_MS / deltaTime)
            .append(", Delta: ")
            .append(deltaTime)
            .toString()
        );
        return deltaTime;
    }

    private long calculateDeltaTime(final long lastFrameTime) {
        return System.currentTimeMillis() - lastFrameTime;
    }

}
