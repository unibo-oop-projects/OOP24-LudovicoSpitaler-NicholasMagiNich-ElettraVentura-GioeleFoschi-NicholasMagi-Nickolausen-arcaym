package arcaym.model.game.components.impl;

import arcaym.common.point.api.Point;
import arcaym.common.vector.api.Vector;
import arcaym.controller.game.core.api.GameState;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.components.api.ComponentsBasedGameObject;
import arcaym.model.game.core.components.impl.AbstractGameComponent;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

public class InputMovementComponent extends AbstractGameComponent {

    private Vector velocity = Vector.zero();

    public InputMovementComponent(ComponentsBasedGameObject gameObject) {
        super(gameObject);
    }

    @Override
    public void setup(EventsSubscriber<GameEvent> gameEventsSubscriber,
            EventsSubscriber<InputEvent> inputEventsSubscriber,
            GameSceneInfo gameScene,
            GameState gameState) {
        super.setup(gameEventsSubscriber, inputEventsSubscriber, gameScene, gameState);

        // Register callbacks for input events
        inputEventsSubscriber.registerCallback(InputEvent.UP, () -> velocity = Vector.of(0, -1));
        inputEventsSubscriber.registerCallback(InputEvent.DOWN, () -> velocity = Vector.of(0, 1));
        inputEventsSubscriber.registerCallback(InputEvent.LEFT, () -> velocity = Vector.of(-1, 0));
        inputEventsSubscriber.registerCallback(InputEvent.RIGHT, () -> velocity = Vector.of(1, 0));
        inputEventsSubscriber.registerCallback(InputEvent.STOP, () -> velocity = Vector.zero());
    }

    @Override
    public void update(long deltaTime, EventsScheduler<GameEvent> eventsScheduler,
            GameSceneInfo gameScene, GameState gameState) {
        Point currentPosition = gameObject().getPosition();
        double newX = currentPosition.x() + (velocity.getX() * deltaTime);
        double newY = currentPosition.y() + (velocity.getY() * deltaTime);
        Point newPosition = Point.of((int) Math.round(newX), (int) Math.round(newY));

        gameObject().setPosition(newPosition);
    }
}
