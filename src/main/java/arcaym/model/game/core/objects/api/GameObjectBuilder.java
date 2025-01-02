package arcaym.model.game.core.objects.api;

import java.util.Optional;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameObject} builder.
 */
@TypeRepresentation
public interface GameObjectBuilder {

    /**
     * Set game world to use.
     * 
     * @param world game world
     * @return next build step
     */
    SecondStep useWorld(GameWorld world);

    /**
     * Get world in use if set.
     * 
     * @return game world
     */
    @FieldRepresentation
    Optional<GameWorld> world();

    /**
     * Interface for the second build step of a {@link GameObjectBuilder}.
     */
    @TypeRepresentation
    interface SecondStep {

        /**
         * Set game events subscriber to use.
         * 
         * @param eventsSubscriber game events subscriber
         * @return next build step
         */
        ThirdStep useGameEventsSubscriber(Events.Subscriber<GameEvent> eventsSubscriber);

        /**
         * Get game events scheduler in use if set.
         * 
         * @return game events scheduler
         */
        @FieldRepresentation
        Optional<Events.Subscriber<GameEvent>> gameEventsSubscriber();

    }

    /**
     * Interface for the third build step of a {@link GameObjectBuilder}.
     */
    @TypeRepresentation
    interface ThirdStep {

        /**
         * Set input events subscriber to use.
         * 
         * @param eventsSubscriber input events subscriber
         * @return next build step
         */
        FourthStep useInputEventsSubscriber(Events.Subscriber<InputEvent> eventsSubscriber);

        /**
         * Get input events scheduler in use if set.
         * 
         * @return input events scheduler
         */
        @FieldRepresentation
        Optional<Events.Subscriber<InputEvent>> inputEventsSubscriber();

    }

    /**
     * Interface for the fourth build step of a {@link GameObjectBuilder}.
     */
    interface FourthStep {

        /**
         * Build and set up game object of a specific type.
         * 
         * @param type game object type
         * @return game object
         */
        GameObject build(GameObjectType type);

    }

}
