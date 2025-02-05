package arcaym.model.game.components;

import java.util.function.Predicate;

import arcaym.model.game.core.components.AbstractGameComponent;
import arcaym.model.game.core.components.ComponentsBasedGameObject;
import arcaym.model.game.core.components.GameComponent;
import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.objects.GameObjectCategory;
import arcaym.model.game.core.objects.GameObjectInfo;
import arcaym.model.game.core.scene.GameSceneInfo;
import arcaym.model.game.events.GameEvent;

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
            final CollisionConsumer reaction, final ComponentsBasedGameObject gameObject) {
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
    public GameComponent obstacleCollision(final ComponentsBasedGameObject gameObject) {
        return genericCollision(
            info -> info.category() == GameObjectCategory.PLAYER,
            (deltaTime, eventsScheduler, collidingObject, gameScene) -> eventsScheduler.scheduleEvent(GameEvent.GAME_OVER), 
            gameObject
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameComponent collectableCollision(final ComponentsBasedGameObject gameObject) {
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
    public GameComponent reachedGoal(final ComponentsBasedGameObject gameObject) {
        return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                }, gameObject);
    }
}
