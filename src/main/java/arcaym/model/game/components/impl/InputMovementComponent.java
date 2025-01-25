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
import arcaym.model.game.events.api.InputEvent;

/**
 * Implementation of {@link AbstractGameComponent} specific for movement from
 * input.
 */
public class InputMovementComponent extends AbstractGameComponent {
    private final Map<InputEvent, Vector> directions = new HashMap<>();
    private final Map<InputEvent, Boolean> activeInputs = new HashMap<>();

    private Vector velocity = Vector.zero();

    /**
     * Basic constructor getting gameObject as an argument.
     * 
     * @param gameObject
     */
    public InputMovementComponent(final ComponentsBasedGameObject gameObject) {
        super(gameObject);
        directions.put(InputEvent.UP, Vector.of(0, -1));
        directions.put(InputEvent.DOWN, Vector.of(0, 1));
        directions.put(InputEvent.LEFT, Vector.of(-1, 0));
        directions.put(InputEvent.RIGHT, Vector.of(1, 0));

        for (InputEvent event : InputEvent.values()) {
            activeInputs.put(event, false);
        }
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

        for (InputEvent event : InputEvent.values()) {
            inputEventsSubscriber.registerCallback(event, e -> activeInputs.put(event, e.isActive()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
            final GameSceneInfo gameScene, final GameState gameState) {

        velocity = Vector.zero();
        activeInputs.entrySet().forEach(entry->{
            if (entry.getValue()) {
                velocity = velocity.sum(directions.get(entry.getKey()));
            }
        });
        final Point currentPosition = gameObject().getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        final Point newPosition = Point.of(Math.round(newX), Math.round(newY));

        gameObject().setPosition(newPosition);
    }
}
