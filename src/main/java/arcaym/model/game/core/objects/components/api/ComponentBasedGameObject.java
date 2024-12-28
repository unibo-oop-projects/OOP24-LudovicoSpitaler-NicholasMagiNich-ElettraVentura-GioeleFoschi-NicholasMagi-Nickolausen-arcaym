package arcaym.model.game.core.objects.components.api;

import java.util.stream.Stream;

import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameObject} using a component system.
 */
public interface ComponentBasedGameObject extends GameObject {

    /**
     * Add a component to the object.
     * 
     * @param component component to add
     */
    void addComponent(GameObjectComponent component);

    /**
     * Remove a component from the object.
     * 
     * @param component to remove
     */
    void removeComponent(GameObjectComponent component);

    /**
     * Get a stream of all the components.
     * 
     * @return components stream
     */
    Stream<GameObjectComponent> componentsStream();

}
