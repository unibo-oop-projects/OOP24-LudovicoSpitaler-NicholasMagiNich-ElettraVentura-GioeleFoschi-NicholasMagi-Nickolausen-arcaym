package arcaym.view.app;

import arcaym.controller.editor.api.EditorController;
import arcaym.controller.game.api.GameController;
import arcaym.controller.menu.MenuController;
import arcaym.controller.shop.ShopController;
import arcaym.view.editor.api.EditorView;
import arcaym.view.game.api.GameView;
import arcaym.view.menu.MenuView;
import arcaym.view.shop.api.ShopView;

/**
 * Interface for the main app view.
 */
public interface MainView extends View {

    /**
     * Initializes the view.
     * 
     * @return {@code true} if the view has been initialized, {@code false} otherwise
     */
    boolean init();

    /**
     * Create and switch to a menu view.
     * 
     * @param controller menu controller
     * @return resulting view
     */
    MenuView switchToMenu(MenuController controller);

    /**
     * Create and switch to an editor view.
     * 
     * @param controller editor controller
     * @return resulting view
     */
    EditorView switchToEditor(EditorController controller);

    /**
     * Create and switch to a game view.
     * 
     * @param controller game controller
     * @return resulting view
     */
    GameView switchToGame(GameController controller);

    /**
     * Create and switch to a shop view.
     * 
     * @param controller shop controller
     * @return resulting view
     */
    ShopView switchToShop(ShopController controller);
}
