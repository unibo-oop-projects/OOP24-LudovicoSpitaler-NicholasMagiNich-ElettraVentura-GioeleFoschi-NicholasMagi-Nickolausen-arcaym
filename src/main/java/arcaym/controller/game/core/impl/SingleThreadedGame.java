package arcaym.controller.game.core.impl;

import java.util.Objects;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.scene.api.GameScene;
import arcaym.view.game.api.GameView;

public class SingleThreadedGame implements Game {

    private final GameScene scene;
    private final GameView view;

    public SingleThreadedGame(final GameScene scene, final GameView view) {
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
    
}
