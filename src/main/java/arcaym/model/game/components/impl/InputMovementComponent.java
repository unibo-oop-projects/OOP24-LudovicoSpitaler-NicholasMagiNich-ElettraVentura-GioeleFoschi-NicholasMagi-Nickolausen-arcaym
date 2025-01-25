package arcaym.model.game.components.impl;

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

    private Vector velocity = Vector.zero();

    /**
     * Basic constructor getting gameObject as an argument.
     * 
     * @param gameObject
     */
    public InputMovementComponent(final ComponentsBasedGameObject gameObject) {
        super(gameObject);
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

        // TO FIX

        inputEventsSubscriber.registerCallback(InputEvent.UP, () -> velocity = Vector.of(0, -1));
        inputEventsSubscriber.registerCallback(InputEvent.DOWN, () -> velocity = Vector.of(0, 1));
        inputEventsSubscriber.registerCallback(InputEvent.LEFT, () -> velocity = Vector.of(-1, 0));
        inputEventsSubscriber.registerCallback(InputEvent.RIGHT, () -> velocity = Vector.of(1, 0));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final long deltaTime, final EventsScheduler<GameEvent> eventsScheduler,
            final GameSceneInfo gameScene, final GameState gameState) {
        final Point currentPosition = gameObject().getPosition();
        final double newX = currentPosition.x() + (velocity.x() * deltaTime);
        final double newY = currentPosition.y() + (velocity.y() * deltaTime);
        final Point newPosition = Point.of((int) Math.round(newX), (int) Math.round(newY));

        gameObject().setPosition(newPosition);
    }
}
