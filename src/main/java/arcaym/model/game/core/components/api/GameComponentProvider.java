package arcaym.model.game.core.components.api;

import java.util.Collection;

import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameComponent} provider.
 */
public interface GameComponentProvider {

    /**
     * Create a collection of components from a game object type.
     * 
     * @param type game object type
     * @return the components
     */
    Collection<GameComponent> ofType(GameObjectType type);
    
}
