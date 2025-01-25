package arcaym.model.game.components.impl;

import java.util.function.Predicate;
import java.util.stream.Stream;

import arcaym.common.geometry.impl.Rectangles;
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
public class CollisionComponentsFactoryImpl implements CollisionComponentsFactory {

    /**
     * An interface for a consumer that handles a collision.
     */
    interface CollisionConsumer {
        void reactToCollision(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
                GameObjectInfo collidingObject, GameSceneInfo gameScene);
    }

    private Stream<GameObjectInfo> getCollidingObjects(final GameSceneInfo scene,
            final UniqueComponentsGameObject gameObject) {
        return scene.getGameObjectsInfos().stream()
                .filter(obj -> Rectangles.intersecting(obj.boundaries(), gameObject.boundaries()));
    }

    private GameComponent genericCollision(final Predicate<GameObjectInfo> predicateFromObjectInfo,
            final CollisionConsumer reaction, final UniqueComponentsGameObject gameObject) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                getCollidingObjects(gameScene, gameObject)
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
        if (gameObject.type().equals(GameObjectType.COIN)) {
            return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.INCREMENT_SCORE);
                        gameScene.scheduleDestruction(gameObject);
                    }, gameObject);
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
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
