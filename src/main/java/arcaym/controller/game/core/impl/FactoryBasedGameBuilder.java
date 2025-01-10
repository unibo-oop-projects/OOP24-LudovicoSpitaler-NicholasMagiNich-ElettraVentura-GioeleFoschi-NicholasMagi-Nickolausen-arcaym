package arcaym.controller.game.core.impl;

import arcaym.common.point.api.Point;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameBuilder;
import arcaym.controller.game.core.scene.api.GameSceneManager;
import arcaym.controller.game.core.scene.impl.FactoryBasedGameSceneManager;
import arcaym.model.game.core.objects.api.GameObjectFactory;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.view.game.api.GameView;

public class FactoryBasedGameBuilder implements GameBuilder {

    private final GameSceneManager gameScene;
    private boolean consumed = false;

    public FactoryBasedGameBuilder(final GameObjectFactory factory) {
        this.gameScene = new FactoryBasedGameSceneManager(factory);
    }

    @Override
    public GameBuilder addObject(final GameObjectType type, final Point position) {
        if (consumed) {
            throw new IllegalStateException("Builder already consumed");
        }

        this.gameScene.scheduleCreation(type, position);
        return this;
    }

    @Override
    public Game build(final GameView view) {
        this.consumed = true;
        return new SingleThreadedGame(this.gameScene, view);
    }
    
}
