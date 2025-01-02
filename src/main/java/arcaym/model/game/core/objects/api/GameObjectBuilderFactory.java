package arcaym.model.game.core.objects.api;

import java.util.Collection;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.components.api.GameComponentProvider;
import arcaym.model.game.core.objects.impl.BaseGameObjectBuilderFactory;

public interface GameObjectBuilderFactory {

    /**
     * Get new instance of the default factory implementation.
     * 
     * @return factory instance
     */
    static GameObjectBuilderFactory newDefault() {
        return new BaseGameObjectBuilderFactory();
    }

    /**
     * Create builder for a game object that uses the given components.
     * 
     * @param components game components
     * @return resulting builder
     */
    GameObjectBuilder ofComponents(Collection<GameComponent> components);

    /**
     * Create builder for a game object that uses components from a factory.
     * 
     * @param componentsProvider game components factory
     * @return resulting builder
     */
    GameObjectBuilder ofComponentsProvider(GameComponentProvider componentsProvider);

}
