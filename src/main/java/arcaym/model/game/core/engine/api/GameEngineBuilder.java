package arcaym.model.game.core.engine.api;

import java.util.Set;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameEngine} builder.
 */
public interface GameEngineBuilder {

    /**
     * Set game objects to use.
     * 
     * @param gameObjects game objects
     * @return next build step
     */
    SecondStep useGameObjects(Set<GameObject> gameObjects);

    /**
     * Interface for the second build step of a {@link GameEngineBuilder}.
     */
    interface SecondStep {

        /**
         * Set game view to use.
         * 
         * @param view game view
         * @return next build step
         */
        ThirdStep useGameView(GameView view);

    }

    /**
     * Interface for the third build step of a {@link GameEngineBuilder}.
     */
    interface ThirdStep {

        /**
         * Build and set up game engine.
         * 
         * @return game engine
         */
        GameEngine build();

    }

}
