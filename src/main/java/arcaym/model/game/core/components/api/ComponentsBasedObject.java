package arcaym.model.game.core.components.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameObject} that uses a collection of {@link GameComponent}.
 */
public interface ComponentsBasedObject extends GameObject {

    /**
     * Add game component.
     * 
     * @param gameComponent game component
     */
    void addComponent(GameComponent gameComponent);

    /**
     * Remove game component.
     * 
     * @param gameComponent game component
     */
    void removeComponent(GameComponent gameComponent);

    /**
     * Get current components.
     * 
     * @return game components collection
     */
    Collection<GameComponent> getGameComponents();

}
