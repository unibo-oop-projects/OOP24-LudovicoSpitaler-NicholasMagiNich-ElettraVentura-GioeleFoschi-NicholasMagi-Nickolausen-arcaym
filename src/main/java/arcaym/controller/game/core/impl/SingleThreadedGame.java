package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link Game} that uses a single background thread.
 */
public class SingleThreadedGame implements Game {

    private static final String GAME_LOOP_THREAD_NAME = "GameLoopThread";

    private final GameSceneManager scene;
    private final GameView view;
    private final Thread gameLoopThread = Thread.ofPlatform()
                                            .name(GAME_LOOP_THREAD_NAME)
                                            .daemon()
                                            .unstarted(this::gameLoop);

    SingleThreadedGame(final GameSceneManager scene, final GameView view) {
        this.scene = Objects.requireNonNull(scene);
        this.view = Objects.requireNonNull(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void start() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'start'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void abort() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'abort'");
    }

    private void gameLoop() {

    }

}
