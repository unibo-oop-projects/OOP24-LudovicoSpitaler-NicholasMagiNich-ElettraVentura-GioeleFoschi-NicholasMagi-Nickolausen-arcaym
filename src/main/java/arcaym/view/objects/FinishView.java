package arcaym.view.objects;

import java.io.IOException;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of the ending tile.
 */
public class FinishView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "finish_tile.png";

    /**
     * Default constructor, which loads the icon.
     */
    public FinishView() {
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
        return GameObjectCategory.GOAL;
    }
}
