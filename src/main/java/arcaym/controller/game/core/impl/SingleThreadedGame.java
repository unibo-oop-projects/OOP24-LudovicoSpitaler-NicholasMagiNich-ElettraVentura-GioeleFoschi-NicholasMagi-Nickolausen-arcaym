package arcaym.controller.game.core.impl;

import java.util.logging.Logger;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.scene.api.GameScene;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame extends AbstractThreadSafeGame {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";
    private static final long GAME_LOOP_PERIOD_MS = 10;
    private static final Logger LOGGER = Logger.getLogger(SingleThreadedGame.class.getName());
    private volatile boolean runGameLoop;
    private final Thread gameLoopThread = Thread.ofPlatform()
                                            .name(GAME_LOOP_THREAD_NAME)
                                            .daemon()
                                            .unstarted(this::gameLoop);

    SingleThreadedGame(final GameScene gameScene, final GameView gameView) {
        super(gameScene, gameView);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        this.runGameLoop = true;
        this.gameLoopThread.start();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleStop() {
        this.runGameLoop = false;
    }

    private void gameLoop() {
        long deltaTime = this.updateDeltaTime(0);
        while (this.runGameLoop) {
            this.inputEventsManager().consumePendingEvents();
            deltaTime = this.updateDeltaTime(deltaTime);
            // no stream beacause delta time is not final
            for (final var gameObject : this.scene().getGameObjects()) {
                gameObject.update(deltaTime, this.gameEventsManager(), this.scene(), this.state());
            }
            this.scene().getGameObjects().forEach(this.view()::updateObject);
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
