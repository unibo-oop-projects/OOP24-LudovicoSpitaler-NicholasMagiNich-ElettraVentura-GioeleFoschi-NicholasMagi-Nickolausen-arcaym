package arcaym.model.game.core.components.api;

import java.util.Collection;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.common.utils.representation.Representation.FieldRepresentation;
import arcaym.common.utils.representation.Representation.TypeRepresentation;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.components.GameComponentFactory;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for basic {@link GameObject} component.
 */
@TypeRepresentation
public interface GameComponent extends InteractiveObject {

    /**
     * Set component game object.
     * 
     * @param object game object
     */
    void setObject(GameObject object);

    /**
     * Get component game object.
     * 
     * @return game object
     */
    @FieldRepresentation
    Optional<GameObject> getObject();

    /**
     * Get component game object.
     * 
     * @return game object
     * @throws IllegalStateException if the game object is missing
     */
    default GameObject getRequiredObject() {
        return Optionals.orIllegalState(this.getObject(), "Missing attached game object");
    }

    /**
     * Interface for a {@link GameComponent} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new GameComponentFactory();
        }

        /**
         * Create a collection of components from a game object type.
         * 
         * @param type game object type
         * @return the components
         */
        Collection<GameComponent> ofType(GameObjectType type);

    }

}
