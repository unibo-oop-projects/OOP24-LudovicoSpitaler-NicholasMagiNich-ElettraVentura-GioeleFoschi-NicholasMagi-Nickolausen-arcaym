package arcaym.model.game.core.objects.api;

import arcaym.common.point.api.Point;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a basic game object.
 */
public interface GameObject extends InteractiveObject {

    /**
     * Get the specific type of the object.
     * 
     * @return game object type
     */
    GameObjectType type();

    /**
     * Get the major category of the object.
     * 
     * @return game object category
     */
    default GameObjectCategory category() {
        return this.type().category();
    }

    /**
     * Get world associated with the object.
     * 
     * @return the game world
     */
    GameWorld world();

    /**
     * Get object position.
     * 
     * @return position
     */
    Point getPosition();

    /**
     * Set object position.
     * 
     * @param position new position
     */
    void setPosition(Point position);

    /**
     * Move object from current position by distance.
     * 
     * @param distance amount to move on each coordinate
     */
    void move(Point distance);

    /**
     * Base interface for representing a {@link GameObject} builder.
     */
    interface StepBuilder extends 
        BuildSteps.First, 
        BuildSteps.Second,
        BuildSteps.Third,
        BuildSteps.Fourth { }

    /**
     * Group of interfaces for the {@link GameObject.StepBuilder} build steps.
     */
    interface BuildSteps {

        /**
         * Interface for the first build step of a {@link GameObject.StepBuilder}.
         */
        interface First {

            /**
             * Set world of the game object.
             * 
             * @param world game world
             * @return next build step
             */
            Second addWorld(GameWorld world);

        }

        /**
         * Interface for the second build step of a {@link GameObject.StepBuilder}.
         */
        interface Second {

            /**
             * Set the game events scheduler to use for the game object.
             * 
             * @param scheduler game events scheduler
             * @return next build step
             */
            Third addGameEventsScheduler(EventsScheduler<GameEvent> scheduler);

        }

        /**
         * Interface for the third build step of a {@link GameObject.StepBuilder}.
         */
        interface Third {

            /**
             * Set the input events scheduler to use for the game object.
             * 
             * @param scheduler input events scheduler
             * @return next build step
             */
            Fourth addInputEventsScheduler(EventsScheduler<InputEvent> scheduler);

        }

        /**
         * Interface for the fourth build step of a {@link GameObject.StepBuilder}.
         */
        interface Fourth {

            /**
             * Build game object of a specific type.
             * 
             * @param type game object type
             * @return resulting game object
             */
            GameObject build(GameObjectType type);

        }

    }

}
