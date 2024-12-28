package arcaym.model.game.core.components.api;


import arcaym.model.game.core.objects.api.GameObjectBuilder;

/**
 * Interface for a {@link GameObjectBuilder} using a component system.
 */
public interface ComponentBasedGameObjectBuilder extends GameObjectBuilder {

    /**
     * Add a component to the build steps.
     * 
     * @param component component to add
     * @return the builder
     */
    ComponentBasedGameObjectBuilder addComponent(GameObjectComponent component);

}
