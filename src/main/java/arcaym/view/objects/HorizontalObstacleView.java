package arcaym.view.objects;

import java.io.IOException;

import arcaym.controller.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of an horizontal-sliding obstacle.
 */
public class HorizontalObstacleView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "h_obstacle.png";

    /**
     * Default constructor, which loads the icon.
     */
    public HorizontalObstacleView() {
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
