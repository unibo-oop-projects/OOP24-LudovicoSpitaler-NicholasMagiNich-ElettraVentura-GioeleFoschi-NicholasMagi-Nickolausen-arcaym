package arcaym.model.game.core.components.api;


import arcaym.model.game.core.components.impl.ComponentsGameObjectBuilderFactory;
import arcaym.model.game.core.objects.api.GameObject;

/**
 * Interface for a {@link GameObject.StepBuilder} using a component system.
 */
public interface ComponentsGameObjectBuilder extends GameObject.StepBuilder {

    /**
     * Add a component to the build steps.
     * 
     * @param component component to add
     * @return the builder
     */
    ComponentsGameObjectBuilder addComponent(GameComponent component);

    /**
     * Interface for a {@link ComponentsGameObjectBuilder} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new ComponentsGameObjectBuilderFactory();
        }

        /**
         * Create builder that accepts all components.
         * 
         * @return resulting builder
         */
        ComponentsGameObjectBuilder baseBuilder();

        /**
         * Create builder that gets components from a factory.
         * 
         * @param componentsFactory components factory
         * @return resulting builder at final build steps
         */
        GameObject.StepBuilder ofComponentsFactory(GameComponent.Factory componentsFactory);

    }

}
