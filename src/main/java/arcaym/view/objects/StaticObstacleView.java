package arcaym.view.objects;

import java.io.IOException;

import arcaym.controller.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of a non-moving obstacle.
 */
public class StaticObstacleView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "static_obstacle.png";

    /**
     * Default constructor, which loads the icon.
     */
    public StaticObstacleView() {
        try {
            loadSprite(iconPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameObjectCategory getCategory() {
        return GameObjectCategory.OBSTACLE;
    }
}
