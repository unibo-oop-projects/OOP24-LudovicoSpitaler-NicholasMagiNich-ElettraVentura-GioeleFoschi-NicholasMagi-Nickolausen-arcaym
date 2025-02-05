package arcaym.controller.app;

import java.util.function.BiFunction;

import arcaym.controller.editor.api.ExtendedEditorController;
import arcaym.controller.game.ExtendedGameController;
import arcaym.controller.menu.ExtendedMenuController;
import arcaym.controller.shop.ExtendedShopController;
import arcaym.view.app.MainView;
import arcaym.view.app.View;

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
     *          // it is assumed the class has access to the switcher
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
    <V extends View, C extends ExtendedController<V>> void switchTo(C controller, BiFunction<MainView, C, V> viewCreator);

    /**
     * Call {@link #switchTo(ExtendedController, BiFunction)} using 
     * {@link MainView#switchToMenu(ExtendedMenuController)} to create the view.
     * 
     * @param controller menu controller
     */
    default void switchToMenu(final ExtendedMenuController controller) {
        this.switchTo(controller, MainView::switchToMenu);
    }

    /**
     * Call {@link #switchTo(ExtendedController, BiFunction)} using 
     * {@link MainView#switchToEditor(ExtendedEditorController)} to create the view.
     * 
     * @param controller editor controller
     */
    default void switchToEditor(final ExtendedEditorController controller) {
        this.switchTo(controller, MainView::switchToEditor);
    }

    /**
     * Call {@link #switchTo(ExtendedController, BiFunction)} using 
     * {@link MainView#switchToGame(ExtendedGameController)} to create the view.
     * 
     * @param controller game controller
     */
    default void switchToGame(final ExtendedGameController controller) {
        this.switchTo(controller, MainView::switchToGame);
    }

    /**
     * Call {@link #switchTo(ExtendedController, BiFunction)} using 
     * {@link MainView#switchToShop(ExtendedShopController)} to create the view.
     * 
     * @param controller shop controller
     */
    default void switchToShop(final ExtendedShopController controller) {
        this.switchTo(controller, MainView::switchToShop);
    }

}
