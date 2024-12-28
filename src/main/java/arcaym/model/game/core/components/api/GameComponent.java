package arcaym.model.game.core.components.api;

import java.util.Optional;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.InteractiveObject;

/**
 * Interface for basic {@link GameObject} component.
 */
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
    Optional<GameObject> getObject();

    /**
     * Get component game object.
     * 
     * @throws IllegalStateException if the game object is missing
     * @return game object
     */
    default GameObject getRequiredObject() {
        return this.getObject().orElseThrow(() -> new IllegalStateException("Missing attached game object"));
    }

}
