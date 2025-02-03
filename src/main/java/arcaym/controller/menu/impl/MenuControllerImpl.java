package arcaym.controller.menu.impl;

import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.impl.AbstractController;
import arcaym.controller.editor.impl.EditorControllerImpl;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.menu.api.ExtendedMenuController;
import arcaym.controller.shop.impl.ShopControllerImpl;
import arcaym.model.editor.EditorType;
import arcaym.view.menu.api.MenuView;

/**
 * Implementation of {@link ExtendedMenuController}.
 */
public class MenuControllerImpl 
    extends AbstractController<MenuView>
    implements ExtendedMenuController {

    /**
     * Initialize menu.
     * 
     * @param switcher controller switcher
     */
    public MenuControllerImpl(final ControllerSwitcher switcher) {
        super(switcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openEditor(final LevelMetadata levelMetadata) {
        this.switcher().switchToEditor(new EditorControllerImpl(levelMetadata, this.switcher()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createEditor(final int width, final int height, final EditorType type, final String name) {
        this.switcher().switchToEditor(new EditorControllerImpl(
            width,
            height,
            type,
            name, 
            this.switcher()
        ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openShop() {
        this.switcher().switchToShop(new ShopControllerImpl(this.switcher()));
    }

}
