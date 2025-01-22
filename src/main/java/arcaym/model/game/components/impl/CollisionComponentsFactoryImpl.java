package arcaym.model.game.components.impl;

import java.util.function.Predicate;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.components.api.CollisionComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Implementation of a {@link CollisionComponentFactory}.
 */
public class CollisionComponentsFactoryImpl extends AbstractComponentsFactory implements CollisionComponentsFactory {

    /**
     * Constructor getting gameObject as an argument.
     * 
     * @param gameObject
     */
    public CollisionComponentsFactoryImpl(final UniqueComponentsGameObject gameObject) {
        super(gameObject);
    }

    interface CollisionConsumer {
        void reactToCollision(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                GameObjectInfo collidingObject, GameSceneInfo gameScene);
    }

    private GameComponent genericCollision(final Predicate<GameObjectInfo> predicateFromObjectInfo,
            final CollisionConsumer reaction) {
        return new AbstractGameComponent(getGameObject()) {

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                getCollisionHandler().getCollidingObjects(gameScene)
                        .filter(predicateFromObjectInfo::test)
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj, gameScene));
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent obstacleCollision() {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent collectableCollision() {
        if (getGameObject().type().equals(GameObjectType.COIN)) {
            return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.INCREMENT_SCORE);
                        gameScene.scheduleDestruction(getGameObject());
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent reachedGoal() {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                });
    }
}
