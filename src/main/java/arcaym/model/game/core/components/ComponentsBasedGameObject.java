package arcaym.model.game.core.components;

import arcaym.model.game.core.objects.GameObject;

/**
 * Interface for a {@link GameObject} that uses a collection of {@link GameComponent}.
 */
public interface ComponentsBasedGameObject extends GameObject {

    /**
     * Add game component.
     * 
     * @param component game component
     */
    void addComponent(GameComponent component);

}
