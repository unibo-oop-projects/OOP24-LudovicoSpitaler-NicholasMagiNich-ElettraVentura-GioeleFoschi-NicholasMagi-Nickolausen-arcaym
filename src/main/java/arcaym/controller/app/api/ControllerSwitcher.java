package arcaym.controller.app.api;

import java.util.function.BiFunction;

import arcaym.controller.editor.api.EditorController;
import arcaym.controller.game.api.GameController;
import arcaym.controller.menu.api.MenuController;
import arcaym.view.app.api.MainView;
import arcaym.view.app.api.View;

/**
 * Interface for a {@link MainController} switch function.
 */
@FunctionalInterface
public interface ControllerSwitcher {

    /**
     * Switch to given controller, then create and attach a view from it.
     * 
     * <p>
     * Example of usage:
     * <pre>
     * class EditorControllerImpl implements EditorController {
     *      ...
     *      void play() {
     *          // is is assumed the class has access to the switcher
     *          GameController gameController = // create game controller
     *          switcher.switchTo(gameController, MainView::switchToGame);
     *      }
     *      ...
     * }
     * </pre
     * </p>
     * 
     * @param <V> view type
     * @param <C> controller type
     * @param controller controller
     * @param viewCreator view creator
     */
    <V extends View, C extends Controller<V>> void switchTo(C controller, BiFunction<MainView, C, V> viewCreator);

    /**
     * Call {@link #switchTo(Controller, BiFunction)} using {@link MainView#switchToMenu(MenuController)} to create the view.
     * 
     * @param controller menu controller
     */
    default void switchToMenu(final MenuController controller) {
        this.switchTo(controller, MainView::switchToMenu);
    }

    /**
     * Call {@link #switchTo(Controller, BiFunction)} using {@link MainView#switchToEditor(EditorController)} to create the view.
     * 
     * @param controller editor controller
     */
    default void switchToEditor(final EditorController controller) {
        this.switchTo(controller, MainView::switchToEditor);
    }

    /**
     * Call {@link #switchTo(Controller, BiFunction)} using {@link MainView#switchToGame(GameController)} to create the view.
     * 
     * @param controller game controller
     */
    default void switchToGame(final GameController controller) {
        this.switchTo(controller, MainView::switchToGame);
    }

}
