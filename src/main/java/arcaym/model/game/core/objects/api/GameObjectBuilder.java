package arcaym.model.game.core.objects.api;

import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.GameEvent;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameObject} builder.
 */
public interface GameObjectBuilder {

    /**
     * Set game world to use.
     * 
     * @param scene game scene
     * @return next build step
     */
    ScoreStep useScene(GameScene scene);

    /**
     * Interface for the game score build step of a {@link GameObjectBuilder}.
     */
    interface ScoreStep {

        /**
         * Set game score to use.
         * 
         * @param gameScore game score
         * @return next build step
         */
        GameEventsStep useScore(GameScore gameScore);

    }

    /**
     * Interface for the game events build step of a {@link GameObjectBuilder}.
     */
    interface GameEventsStep {

        /**
         * Set game events subscriber to use.
         * 
         * @param eventsSubscriber game events subscriber
         * @return next build step
         */
        InputEventsStep useGameEventsSubscriber(Events.Subscriber<GameEvent> eventsSubscriber);

    }

    /**
     * Interface for the input events build step of a {@link GameObjectBuilder}.
     */
    interface InputEventsStep {

        /**
         * Set input events subscriber to use.
         * 
         * @param eventsSubscriber input events subscriber
         * @return next build step
         */
        BuildStep useInputEventsSubscriber(Events.Subscriber<InputEvent> eventsSubscriber);

    }

    /**
     * Interface for the final build step of a {@link GameObjectBuilder}.
     */
    interface BuildStep {

        /**
         * Build and set up game object of a specific type.
         * 
         * @param type game object type
         * @return game object
         */
        GameObject build(GameObjectType type);

    }

}
