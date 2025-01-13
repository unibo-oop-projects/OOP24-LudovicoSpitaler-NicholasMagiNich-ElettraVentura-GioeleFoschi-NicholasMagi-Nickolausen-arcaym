package arcaym.model.game.core.objects.api;

/**
 * Interface for a {@link GameObject} factory.
 */
public interface GameObjectFactory {

    /**
     * Create game object of a specific type.
     * 
     * @param type game object type
     * @return resulting game object
     */
    GameObject ofType(GameObjectType type);

}
