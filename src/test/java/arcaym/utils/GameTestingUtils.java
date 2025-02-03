package arcaym.utils;

import arcaym.model.game.components.impl.ComponentsBasedObjectsFactory;
import arcaym.model.game.core.objects.api.GameObjectsFactory;

public final class GameTestingUtils {

    public static final int TILE_SIZE = 10;

    private GameTestingUtils() { }

    public static final GameObjectsFactory createObjectsFactory() {
        return new ComponentsBasedObjectsFactory(TILE_SIZE);
    }
    
}
