package arcaym.model.game.core.objects.api;

import java.util.Collection;

import arcaym.common.point.api.Point;
import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.objects.impl.GameObjectBuilderFactory;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a basic game object.
 */
@TypeRepresentation
public interface GameObject extends InteractiveObject, GameObjectView {

    /**
     * Get world associated with the object.
     * 
     * @return the game world
     */
    @FieldRepresentation
    GameWorld world();

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
     * Interface for a {@link GameObject} builder.
     */
    interface Builder extends 
        BuildSteps.First, 
        BuildSteps.Second,
        BuildSteps.Third,
        BuildSteps.Fourth {

            /**
             * Interface for a {@link GameObject.Builder} factory.
             */
            interface Factory {

                /**
                 * Get new instance of the default factory implementation.
                 * 
                 * @return factory instance
                 */
                static Factory newDefault() {
                    return new GameObjectBuilderFactory();
                }

                /**
                 * Create builder for a game object that uses the given components.
                 * 
                 * @param components game components
                 * @return resulting builder
                 */
                BuildSteps.First ofComponents(Collection<GameComponent> components);

                /**
                 * Create builder for a game object that uses components from a factory.
                 * 
                 * @param componentsFactory game components factory
                 * @return resulting builder
                 */
                BuildSteps.First ofComponentsFactory(GameComponent.Factory componentsFactory);

            }

    }

    /**
     * Group of interfaces for the {@link GameObject.Builder} build steps.
     */
    interface BuildSteps {

        /**
         * Interface for the first build step of a {@link GameObject.Builder}.
         */
        interface First {

            /**
             * Set game world to use.
             * 
             * @param world game world
             * @return next build step
             */
            Second useWorld(GameWorld world);

        }

        /**
         * Interface for the second build step of a {@link GameObject.Builder}.
         */
        interface Second {

            /**
             * Set game events subscriber to use.
             * 
             * @param eventsSubscriber game events subscriber
             * @return next build step
             */
            Third useGameEventsSubscriber(Events.Subscriber<GameEvent> eventsSubscriber);

        }

        /**
         * Interface for the third build step of a {@link GameObject.Builder}.
         */
        interface Third {

            /**
             * Set input events subscriber to use.
             * 
             * @param eventsSubscriber input events subscriber
             * @return next build step
             */
            Fourth useInputEventsSubscriber(Events.Subscriber<InputEvent> eventsSubscriber);

        }

        /**
         * Interface for the fourth build step of a {@link GameObject.Builder}.
         */
        interface Fourth {

            /**
             * Build and set up game object of a specific type.
             * 
             * @param type game object type
             * @return resulting game object
             */
            GameObject build(GameObjectType type);

        }

    }

}
