package arcaym.view.objects;

import java.io.IOException;

import arcaym.model.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of a collectable coin.
 */
public class CoinView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "coin.png";

    /**
     * Default constructor, which loads the icon.
     */
    public CoinView() {
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
