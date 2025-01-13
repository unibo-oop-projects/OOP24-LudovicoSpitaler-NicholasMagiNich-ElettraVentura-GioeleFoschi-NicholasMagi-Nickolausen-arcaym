package arcaym.view.objects;

import java.io.IOException;

import arcaym.controller.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of a vertical-sliding obstacle.
 */
public class VerticalObstacleView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "v_obstacle.png";

    /**
     * Default contstructor, which loads the icon.
     */
    public VerticalObstacleView() {
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
