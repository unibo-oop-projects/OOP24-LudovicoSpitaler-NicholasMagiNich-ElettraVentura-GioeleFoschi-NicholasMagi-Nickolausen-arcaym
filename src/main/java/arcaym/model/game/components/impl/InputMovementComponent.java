package arcaym.model.game.components.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.api.GameSceneInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;

/**
 * Implementation of {@link AbstractGameComponent} specific for movement from
 * input.
 */
public class InputMovementComponent extends AbstractGameComponent {
    
    private final List<InputDirection> directions = List.of(
        new InputDirection(InputType.UP, Vector.of(0, -1)),
        new InputDirection(InputType.DOWN, Vector.of(0, 1)),
        new InputDirection(InputType.RIGHT, Vector.of(1, 0)),
        new InputDirection(InputType.LEFT, Vector.of(-1, 0))
    );
    private final Map<Vector, Boolean> activeDirections;
    private final GameObject gameObject;

    private record InputDirection(InputType inputType, Vector direction) {}

    /**
     * Basic constructor getting gameObject as an argument.
     * 
     * @param gameObject game object
     */
    public InputMovementComponent(final ComponentsBasedGameObject gameObject) {
        super(gameObject);
        this.gameObject = gameObject;
        activeDirections = directions.stream().collect(Collectors.toMap(InputDirection::direction, v -> false));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(final EventsSubscriber<GameEvent> gameEventsSubscriber,
            final EventsSubscriber<InputEvent> inputEventsSubscriber,
            final GameSceneInfo gameScene,
            final GameStateInfo gameState) {
        super.setup(gameEventsSubscriber, inputEventsSubscriber, gameScene, gameState);

        directions.forEach(inputDir -> {
            List.of(true, false).forEach(drop -> {
                inputEventsSubscriber.registerCallback(
                    new InputEvent(inputDir.inputType, drop),
                    event -> activeDirections.put(inputDir.direction, !drop)
                );
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
            final GameSceneInfo gameScene, final GameStateInfo gameState) {
        Vector vel = Vector.zero();
        for (final var entry : activeDirections.entrySet()) {
            if (entry.getValue()) {
                vel = vel.sum(entry.getKey());
            }
        }

        final Point currentPosition = gameObject().getPosition();
        final double newX = currentPosition.x() + (vel.x() * deltaTime);
        final double newY = currentPosition.y() + (vel.y() * deltaTime);
        final Point newPosition = Point.of(newX, newY);

        if (!CollisionUtils.isWallCollisionActive(gameScene, gameObject) && gameState.boundaries().contains(gameObject.boundaries())) {                  
            gameObject.setPosition(newPosition);
        }
    }
}
