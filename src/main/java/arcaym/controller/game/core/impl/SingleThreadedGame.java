package arcaym.controller.game.core.impl;

import java.util.logging.Logger;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame extends AbstractThreadSafeGame {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";
    private static final long GAME_LOOP_PERIOD_MS = 10;
    private static final Logger LOGGER = Logger.getLogger(SingleThreadedGame.class.getName());

    private final Thread gameLoopThread = Thread.ofPlatform()
                                            .name(GAME_LOOP_THREAD_NAME)
                                            .daemon()
                                            .unstarted(this::gameLoop);

    SingleThreadedGame(final GameSceneManager scene, final GameView view) {
        super(scene, view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.gameLoopThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abort() {
        this.gameLoopThread.interrupt();
    }

    private void gameLoop() {
        long deltaTime = this.updateDeltaTime(0);
        while (!this.gameLoopThread.isInterrupted()) {
            this.inputEventsManager().consumePendingEvents();
            deltaTime = this.updateDeltaTime(deltaTime);
            for (final var gameObject : this.scene().gameObjects()) {
                gameObject.update(deltaTime, this.gameEventsManager(), this.scene());
            }
            this.scene().consumePendingActions();
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
