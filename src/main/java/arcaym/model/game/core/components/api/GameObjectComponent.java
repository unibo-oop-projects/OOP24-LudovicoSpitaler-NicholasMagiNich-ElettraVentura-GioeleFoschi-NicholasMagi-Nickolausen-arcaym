package arcaym.model.game.core.components.api;

import java.util.Collection;
import java.util.Optional;

import arcaym.common.utils.Optionals;
import arcaym.model.game.core.engine.api.InteractiveObject;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for basic {@link GameObject} component.
 */
public interface GameObjectComponent extends InteractiveObject {

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
     * Interface for a {@link GameObjectComponent} factory.
     */
    interface Factory {

        /**
         * Create a collection of components associated with a game object type.
         * 
         * @param type game object type
         * @return the components
         */
        Collection<GameObjectComponent> ofType(GameObjectType type);

    }

}
