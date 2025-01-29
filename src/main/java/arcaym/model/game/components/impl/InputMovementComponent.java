package arcaym.model.game.components.impl;

import java.util.HashMap;
import java.util.Map;

import arcaym.common.geometry.impl.Point;
import arcaym.common.geometry.impl.Vector;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;

/**
 * Implementation of {@link AbstractGameComponent} specific for movement from
 * input.
 */
public class InputMovementComponent extends AbstractGameComponent {
    private final Map<Vector, Boolean> activeDirections = new HashMap<>();

    /**
     * Basic constructor getting gameObject as an argument.
     * 
     * @param gameObject
     */
    public InputMovementComponent(final ComponentsBasedGameObject gameObject) {
        super(gameObject);
        activeDirections.put(Vector.of(0, -1), false);
        activeDirections.put(Vector.of(0, 1), false);
        activeDirections.put(Vector.of(-1, 0), false);
        activeDirections.put(Vector.of(1, 0), false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setup(final EventsSubscriber<GameEvent> gameEventsSubscriber,
            final EventsSubscriber<InputEvent> inputEventsSubscriber,
            final GameSceneInfo gameScene,
            final GameState gameState) {
        super.setup(gameEventsSubscriber, inputEventsSubscriber, gameScene, gameState);

        inputEventsSubscriber.registerCallback(new InputEvent(InputType.UP, false),
                event -> activeDirections.put(Vector.of(0, -1), !event.drop()));

        inputEventsSubscriber.registerCallback(new InputEvent(InputType.DOWN, false),
                event -> activeDirections.put(Vector.of(0, 1), !event.drop()));

        inputEventsSubscriber.registerCallback(new InputEvent(InputType.LEFT, false),
                event -> activeDirections.put(Vector.of(-1, 0), !event.drop()));

        inputEventsSubscriber.registerCallback(new InputEvent(InputType.RIGHT, false),
                event -> activeDirections.put(Vector.of(1, 0), !event.drop()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
            final GameSceneInfo gameScene, final GameState gameState) {
        Vector velocity = Vector.zero();
        for (final var entry : activeDirections.entrySet()) {
            if (entry.getValue()) {
                velocity = velocity.sum(entry.getKey());
            }
        }

        final Point currentPosition = gameObject().getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        final Point newPosition = Point.of((int) Math.round(newX), (int) Math.round(newY));

        gameObject().setPosition(newPosition);
    }
}
