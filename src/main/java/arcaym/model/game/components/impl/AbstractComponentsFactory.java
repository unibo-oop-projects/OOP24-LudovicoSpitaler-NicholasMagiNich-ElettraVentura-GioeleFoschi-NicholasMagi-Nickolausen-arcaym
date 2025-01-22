package arcaym.model.game.components.impl;

import arcaym.model.game.components.api.GameComponentsFactory;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.logics.api.CollisionHandler;
import arcaym.model.game.logics.api.MovementHandler;
import arcaym.model.game.logics.impl.BasicCollisionHandler;
import arcaym.model.game.logics.impl.BasicMovementHandler;

/**
 * Abstract class implementing {@link GameComponentsFactory}.
 */
public abstract class AbstractComponentsFactory implements GameComponentsFactory {
    private final UniqueComponentsGameObject gameObject;
    private final CollisionHandler collisionHandler;
    private final MovementHandler movementHandler;

    /**
     * 
     * @return this gameObject
     */
    protected UniqueComponentsGameObject getGameObject() {
        return gameObject;
    }

    /**
     * 
     * @return this collisionHandler
     */
    protected CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    /**
     * 
     * @return this movementHandler
     */
    protected MovementHandler getMovementHandler() {
        return movementHandler;
    }

    /**
     * Constructor with gameObject as an argument.
     * 
     * @param gameObject
     */
    public AbstractComponentsFactory(final UniqueComponentsGameObject gameObject) {
        this.gameObject = gameObject;
        this.collisionHandler = new BasicCollisionHandler(gameObject);
        this.movementHandler = new BasicMovementHandler(gameObject);
    }

}
