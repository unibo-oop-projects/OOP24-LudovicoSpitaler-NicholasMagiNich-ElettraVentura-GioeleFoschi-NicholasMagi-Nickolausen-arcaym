package arcaym.controller.menu.impl;

import arcaym.controller.app.api.ControllerSwitcher;
import arcaym.controller.app.impl.AbstractController;
import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.menu.api.ExtendedMenuController;
import arcaym.controller.shop.impl.ShopControllerImpl;
import arcaym.model.editor.EditorType;
import arcaym.view.menu.api.MenuView;

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
        // TODO fix when editor controlle is ready
        throw new UnsupportedOperationException("Unimplemented method 'openEditor'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createEditor(String name, int width, int height, EditorType type) {
        // TODO fix when editor controlle is ready
        throw new UnsupportedOperationException("Unimplemented method 'createEditor'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void openShop() {
        this.switcher().switchToShop(new ShopControllerImpl(this.switcher()));
    }

}
