package arcaym.view.app.api;

import arcaym.controller.editor.api.EditorController;
import arcaym.controller.game.api.GameController;
import arcaym.controller.menu.api.MenuController;
import arcaym.view.editor.api.EditorView;
import arcaym.view.game.api.GameView;
import arcaym.view.menu.api.MenuView;

/**
 * Interface for the main app view.
 */
public interface MainView extends View {

    /**
     * Create and switch a menu view.
     * 
     * @param controller menu controller
     * @return resulting view
     */
    MenuView switchToMenu(MenuController controller);

    /**
     * Create and switch an editor view.
     * 
     * @param controller editor controller
     * @return resulting view
     */
    EditorView switchToEditor(EditorController controller);

    /**
     * Create and switch a game view.
     * 
     * @param controller game controller
     * @return resulting view
     */
    GameView switchToGame(GameController controller);

}
