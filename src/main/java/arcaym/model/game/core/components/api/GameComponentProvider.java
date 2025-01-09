package arcaym.model.game.core.components.api;

import java.util.Set;

import arcaym.model.game.objects.GameObjectType;

/**
 * Interface for a {@link GameComponent} provider.
 */
public interface GameComponentProvider {

    /**
     * Create components for a game object of a certain type.
     * 
     * @param type game object type
     * @return the components
     */
    Set<GameComponent> ofType(GameObjectType type);

}
