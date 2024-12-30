package arcaym.model.game.core.engine.api;

import java.util.Collection;

import arcaym.model.game.core.events.api.Events;
import arcaym.model.game.core.events.api.InputEvent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.view.api.GameView;

/**
 * Interface for a game manager.
 */
public interface GameEngine extends Events.Scheduler<InputEvent> {

    /**
     * Start game.
     */
    void startGame();

    /**
     * Stop game.
     */
    void stopGame();

    /**
     * Interface for a {@link GameEngine} builder.
     */
    interface Builder extends
        BuildSteps.First,
        BuildSteps.Second,
        BuildSteps.Third { }


    /**
     * Group of interfaces for the {@link GameEngine.Builder} build steps.
     */
    interface BuildSteps {

        /**
         * Interface for the first build step of a {@link GameEngine.Builder}.
         */
        interface First {

            /**
             * Set game objects to use.
             * 
             * @param gameObjects game objects collection
             * @return next build step
             */
            Second useObjects(Collection<GameObject> gameObjects);

        }

        /**
         * Interface for the second build step of a {@link GameEngine.Builder}.
         */
        interface Second {

            /**
             * Set game view to use.
             * 
             * @param view game view
             * @return next build step
             */
            Third useView(GameView view);

        }

        /**
         * Interface for the third build step of a {@link GameEngine.Builder}.
         */
        interface Third {

            /**
             * Build and set up game engine.
             * @return game engine
             */
            GameEngine build();

        }

    }

}
