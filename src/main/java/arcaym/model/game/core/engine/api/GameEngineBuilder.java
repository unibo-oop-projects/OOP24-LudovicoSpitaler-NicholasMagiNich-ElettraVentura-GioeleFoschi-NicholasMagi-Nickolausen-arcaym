package arcaym.model.game.core.engine.api;

import java.util.Collection;
import java.util.Optional;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameEngine} builder.
 */
@TypeRepresentation
public interface GameEngineBuilder {

    /**
     * Set game objects to use.
     * 
     * @param gameObjects game objects collection
     * @return next build step
     */
    SecondStep useObjects(Collection<GameObject> gameObjects);

    /**
     * Get game objects in use if set.
     * 
     * @return game objects
     */
    @FieldRepresentation
    Optional<Collection<GameObject>> gameObjects();

    /**
     * Interface for the second build step of a {@link GameEngineBuilder}.
     */
    @TypeRepresentation
    interface SecondStep {

        /**
         * Set game view to use.
         * 
         * @param view game view
         * @return next build step
         */
        ThirdStep useView(GameView view);

        /**
         * Get game view in use if set.
         * 
         * @return game view
         */
        @FieldRepresentation
        Optional<GameView> view();

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
