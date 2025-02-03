package arcaym.controller.menu.api;

import arcaym.controller.app.api.Controller;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.model.editor.EditorType;

/**
 * Interface for a menu controller.
 */
public interface MenuController extends Controller {

    /**
     * Open editor from existing metadata.
     * 
     * @param levelMetadata level metadata
     */
    void openEditor(LevelMetadata levelMetadata);

    /**
     * Create and open a new editor.
     * 
     * @param width grid width
     * @param height grid height
     * @param type editor type
     * @param name level name
     */
    void createEditor(int width, int height, EditorType type, String name);

    /**
     * Open shop.
     */
    void openShop();

}
