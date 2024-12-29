package arcaym.model.game.core.components.impl;


import arcaym.model.game.core.components.api.ComponentsGameObjectBuilder;
import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.world.api.GameWorld;
import arcaym.model.game.objects.GameObjectType;

/**
 * Basic implementation of {@link ComponentsGameObjectBuilder.Factory}.
 */
public class ComponentsGameObjectBuilderFactory implements ComponentsGameObjectBuilder.Factory {

    private static class BaseBuilder extends AbstractComponentsGameObjectBuilder {
        @Override
        public ComponentsGameObjectBuilder addComponent(final GameComponent component) {
            this.components().add(component);
            return this;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ComponentsGameObjectBuilder baseBuilder() {
        return new BaseBuilder();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObject.StepBuilder ofComponentsFactory(final GameComponent.Factory componentsFactory) {
        return new BaseBuilder() {
            @Override
            protected GameObject newInstance(final GameObjectType type, final GameWorld world) {
                componentsFactory.ofType(type).forEach(this::addComponent);
                return super.newInstance(type, world);
            }
        };
    }

}
