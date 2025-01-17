package arcaym.model.game.components.impl;

import java.util.function.Predicate;

import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.components.impl.UniqueComponentsGameObject;
import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;

public class CollisionComponentsFactory extends AbstractComponentsFactory {

    public CollisionComponentsFactory(final UniqueComponentsGameObject gameObject){
        super(gameObject);
    }

    interface CollisionConsumer {
        void reactToCollision(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                final GameObjectInfo collidingObject, final GameSceneInfo gameScene);
    }

    private GameComponent genericCollision(final Predicate<GameObjectInfo> predicateFromObjectInfo,
            final CollisionConsumer reaction) {
        return new AbstractGameComponent(gameObject) {

            @Override
            public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
                    final GameSceneInfo gameScene,
                    final GameState gameState) {
                collisionHandler.getCollidingObjects(gameScene)
                        .filter(infos -> predicateFromObjectInfo.test(infos))
                        .forEach(obj -> reaction.reactToCollision(deltaTime, eventsScheduler, obj, gameScene));
            }

        };
    }

    public GameComponent obstacleCollision() {
        if (gameObject.type() == GameObjectType.USER_PLAYER) {
            return genericCollision(info -> info.category() == GameObjectCategory.OBSTACLE,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.GAME_OVER);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    public GameComponent collectableCollision() {
        if (gameObject.type().equals(GameObjectType.COIN)) {
            return genericCollision(info -> info.category() == GameObjectCategory.PLAYER,
                    (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                        eventsScheduler.scheduleEvent(GameEvent.INCREMENT_SCORE);
                        gameScene.scheduleDestruction(gameObject);
                    });
        } else {
            throw new IllegalStateException("Unsupported GameObject type for obstacleCollision");
        }
    }

    public GameComponent reachedGoal() {
        return genericCollision(info -> info.category() == GameObjectCategory.GOAL,
                (deltaTime, eventsScheduler, collidingObject, gameScene) -> {
                    eventsScheduler.scheduleEvent(GameEvent.VICTORY);
                });
    }
}
