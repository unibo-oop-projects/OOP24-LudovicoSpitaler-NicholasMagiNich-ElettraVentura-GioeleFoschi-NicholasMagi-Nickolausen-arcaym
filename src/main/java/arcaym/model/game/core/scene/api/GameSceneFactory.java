package arcaym.model.game.core.scene.api;

import java.util.Collection;

import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.scene.impl.BaseGameSceneFactory;

/**
 * Interface for a {@link GameScene} factory.
 */
public interface GameSceneFactory {

    /**
     * Get new instance of the default factory implementation.
     * 
     * @return factory instance
     */
    static GameSceneFactory newDefault() {
        return new BaseGameSceneFactory();
    }

    /**
     * Create a scene that uses the provided collection.
     * 
     * @param collection game objects collection
     * @return resulting scene
     */
    GameScene ofCollection(Collection<GameObject> collection);

    /**
     * Create a scene where the same game object is not allowed twice.
     * 
     * @return game scene
     */
    GameScene ofDistinctObjects();

}
