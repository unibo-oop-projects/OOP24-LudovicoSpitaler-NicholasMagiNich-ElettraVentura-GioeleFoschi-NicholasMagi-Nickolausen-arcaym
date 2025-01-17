package arcaym.model.game.components.impl;

import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.logics.api.CollisionHandler;
import arcaym.model.game.logics.api.MovementHandler;
import arcaym.model.game.logics.impl.BasicCollisionHandler;
import arcaym.model.game.logics.impl.BasicMovementHandler;

public abstract class AbstractComponentsFactory implements GameComponentsFactory {
    protected final UniqueComponentsGameObject gameObject;
    protected final CollisionHandler collisionHandler;
    protected final MovementHandler movementHandler;

    public AbstractComponentsFactory(final UniqueComponentsGameObject gameObject) {
        this.gameObject = gameObject;
        this.collisionHandler = new BasicCollisionHandler(gameObject);
        this.movementHandler = new BasicMovementHandler(gameObject);
    }

}
