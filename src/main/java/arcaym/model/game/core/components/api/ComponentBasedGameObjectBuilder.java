package arcaym.model.game.core.components.api;


import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameObject} builder using a component system.
 */
public interface ComponentBasedGameObjectBuilder {

    /**
     * Add a component to the build steps.
     * 
     * @param component component to add
     * @return the builder
     */
    ComponentBasedGameObjectBuilder addComponent(GameObjectComponent component);

    /**
     * Build the game object with the added components.
     * 
     * @return the game object
     */
    GameObject build();

}
