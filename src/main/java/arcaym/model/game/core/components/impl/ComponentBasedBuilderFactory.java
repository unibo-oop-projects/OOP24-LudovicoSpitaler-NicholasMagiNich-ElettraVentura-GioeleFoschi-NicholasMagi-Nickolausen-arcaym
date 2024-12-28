package arcaym.model.game.core.components.impl;

import java.util.Collection;
import java.util.Objects;
import java.util.function.BiFunction;

import arcaym.model.game.core.components.api.ComponentBasedBuilder;
import arcaym.model.game.core.components.api.GameComponent;

/**
 * Basic implementation of {@link ComponentBasedBuilder.Factory}.
 */
public class ComponentBasedBuilderFactory implements ComponentBasedBuilder.Factory {

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentBasedBuilder ofFilter(
        final BiFunction<Collection<GameComponent>, GameComponent, Boolean> componentsFilter
    ) {
        return new AbstractComponentBasedBuilder() {
            @Override
            public ComponentBasedBuilder addComponent(final GameComponent component) {
                if (componentsFilter.apply(this.components(), Objects.requireNonNull(component))) {
                    this.components().add(component);
                }
                return this;
            }
        };
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentBasedBuilder ofUniqueComponents() {
        return this.ofFilter((currentComponents, component) -> 
            currentComponents.stream()
                .map(Object::getClass)
                .anyMatch(component.getClass()::equals)
        );
    }

}
