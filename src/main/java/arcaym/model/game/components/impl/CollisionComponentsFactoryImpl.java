package arcaym.model.game.components.impl;

import java.util.function.Predicate;

import arcaym.model.game.components.api.CollisionComponentsFactory;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.core.scene.api.GameSceneInfo;
import arcaym.model.game.events.api.GameEvent;

/**
 * Implementation of a {@link CollisionComponentsFactory}.
 */
public class CollisionComponentsFactoryImpl implements CollisionComponentsFactory {

    /**
     * An interface for a consumer that handles a collision.
     */
    interface CollisionConsumer {
        void reactToCollision(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                GameObjectInfo collidingObject, GameSceneInfo gameScene);
    }


    private GameComponent genericCollision(final Predicate<GameObjectInfo> predicateFromObjectInfo,
            final CollisionConsumer reaction, final UniqueComponentsGameObject gameObject) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameStateInfo gameState) {
                CollisionUtils.getCollidingObjects(gameScene, gameObject)
                        .filter(predicateFromObjectInfo::test)
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj, gameScene));
            }

        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent obstacleCollision(final UniqueComponentsGameObject gameObject) {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                }, gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent collectableCollision(final UniqueComponentsGameObject gameObject) {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
            (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                eventsScheduler.scheduleEvent(GameEvent.INCREMENT_SCORE);
                gameScene.scheduleDestruction(gameObject);
            }, 
            gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent reachedGoal(final UniqueComponentsGameObject gameObject) {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                }, gameObject);
    }
}
