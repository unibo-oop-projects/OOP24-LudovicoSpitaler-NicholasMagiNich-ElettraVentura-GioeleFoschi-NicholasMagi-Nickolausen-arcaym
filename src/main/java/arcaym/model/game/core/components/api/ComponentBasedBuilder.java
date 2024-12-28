package arcaym.model.game.core.components.api;


import java.util.Collection;
import java.util.function.BiFunction;

import arcaym.model.game.core.components.impl.ComponentBasedBuilderFactory;
import arcaym.model.game.core.objects.api.GameObjectBuilder;

/**
 * Interface for a {@link GameObjectBuilder} using a component system.
 */
public interface ComponentBasedBuilder extends GameObjectBuilder {

    /**
     * Add a component to the build steps.
     * 
     * @param component component to add
     * @return the builder
     */
    ComponentBasedBuilder addComponent(GameComponent component);

    /**
     * Interface for a {@link ComponentBasedBuilder} factory.
     */
    interface Factory {

        /**
         * Get new instance of the default factory implementation.
         * 
         * @return factory instance
         */
        static Factory newDefault() {
            return new ComponentBasedBuilderFactory();
        }

        /**
         * Create builder with given components filter.
         * 
         * @param componentsFilter (current components, new component) -> if the component should be kept.
         * @return resulting builder
         */
        ComponentBasedBuilder ofFilter(
            BiFunction<Collection<GameComponent>, GameComponent, Boolean> componentsFilter
        );

        /**
         * Create builder with filter to have at most one instance of each type of component.
         * 
         * @return resulting builder
         */
        ComponentBasedBuilder ofUniqueComponents();

    }

}
