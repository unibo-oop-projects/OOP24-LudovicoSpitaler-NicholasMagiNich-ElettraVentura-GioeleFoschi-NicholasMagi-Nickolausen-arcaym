package arcaym.model.game.core.engine.impl;

import java.util.logging.Logger;

import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame extends AbstractThreadSafeGame {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";
    private static final long GAME_LOOP_PERIOD_MS = 10;
    private static final Logger LOGGER = Logger.getLogger(SingleThreadedGame.class.getName());
    private volatile boolean runGameLoop;

    /**
     * Initialize with the given scene.
     * 
     * @param gameScene game scene
     */
    public SingleThreadedGame(final GameScene gameScene) {
        super(gameScene);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start(final GameView gameView) {
        super.start(gameView);
        this.runGameLoop = true;
        Thread.ofPlatform()
                .name(GAME_LOOP_THREAD_NAME)
                .daemon()
                .start(() -> this.gameLoop(gameView));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleStop() {
        this.runGameLoop = false;
    }

    private void gameLoop(final GameView gameView) {
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
    }

    private long updateDeltaTime(final long start) {
        final var deltaTime = System.currentTimeMillis() - start;
        if (deltaTime < GAME_LOOP_PERIOD_MS) {
            try {
                Thread.sleep(GAME_LOOP_PERIOD_MS - deltaTime);
            } catch (InterruptedException e) {
                LOGGER.warning("Update frame waiting interrupted");
            }
        }
        return deltaTime;
    }

}
