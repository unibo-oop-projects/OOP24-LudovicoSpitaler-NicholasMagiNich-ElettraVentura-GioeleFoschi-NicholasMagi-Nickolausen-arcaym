package arcaym.view.objects;

import java.io.IOException;

import arcaym.controller.game.core.objects.api.GameObjectCategory;

/**
 * Visual representation of the player.
 */
public class PlayerView extends GameObjectSwingView {
    private final String iconPath = getResourcesPath() + "start_tile.png";

    /**
     * Default constructor, which loads the icon.
     */
    public PlayerView() {
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
        return GameObjectCategory.PLAYER;
    }
}
