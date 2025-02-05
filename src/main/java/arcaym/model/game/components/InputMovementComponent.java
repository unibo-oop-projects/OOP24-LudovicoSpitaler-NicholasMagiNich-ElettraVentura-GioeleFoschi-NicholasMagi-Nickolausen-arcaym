package arcaym.model.game.components;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Vector;
import arcaym.model.game.core.components.AbstractGameComponent;
import arcaym.model.game.core.components.ComponentsBasedGameObject;
import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.core.scene.GameSceneInfo;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.events.InputEvent;
import arcaym.model.game.events.InputType;

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

    private record InputDirection(InputType inputType, Vector direction) { }

    /**
     * Basic constructor getting gameObject as an argument.
     * 
     * @param gameObject game object
     */
    public InputMovementComponent(final ComponentsBasedGameObject gameObject) {
        super(gameObject);
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

        this.gameObject().setPosition(newPosition);
        if (
            CollisionUtils.isWallCollisionActive(gameScene, this.gameObject()) 
            || !gameState.boundaries().contains(this.gameObject().boundaries())
            ) {
            this.gameObject().setPosition(currentPosition);
        }
    }
}
