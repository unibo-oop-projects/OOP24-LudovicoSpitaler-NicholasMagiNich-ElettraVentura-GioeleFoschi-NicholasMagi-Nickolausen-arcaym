package arcaym.testing.utils;

import arcaym.model.game.components.impl.ComponentsBasedObjectsFactory;
import arcaym.model.game.core.objects.api.GameObjectsFactory;

/**
 * Utility class for game tests.
 */
public final class GameTestingUtils {

    /**
     * Default tile size.
     */
    public static final int TILE_SIZE = 10;

    private GameTestingUtils() { }

    /**
     * Create a test game objects factory.
     * 
     * @return resulting factory
     */
    public static GameObjectsFactory createObjectsFactory() {
        return new ComponentsBasedObjectsFactory(TILE_SIZE);
    }

}
