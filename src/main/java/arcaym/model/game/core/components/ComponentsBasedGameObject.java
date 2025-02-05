package arcaym.model.game.core.components;

import java.util.Set;

import arcaym.model.game.core.objects.GameObject;

/**
 * Interface for a {@link GameObject} that uses a collection of {@link GameComponent}.
 */
public interface ComponentsBasedGameObject extends GameObject {

    /**
     * Set game components.
     * 
     * @param components game components
     */
    void setComponents(Set<GameComponent> components);

}
