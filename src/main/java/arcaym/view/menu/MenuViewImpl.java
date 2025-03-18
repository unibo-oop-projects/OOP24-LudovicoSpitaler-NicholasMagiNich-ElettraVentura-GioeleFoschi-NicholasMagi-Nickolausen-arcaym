package arcaym.view.menu;

import java.awt.FlowLayout;
import java.nio.file.Path;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arcaym.controller.menu.MenuController;
import arcaym.view.app.AbstractView;
import arcaym.view.components.HorizontalCenteredPanel;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.ViewComponent;
import arcaym.view.menu.components.CreateLevelDialog;
import arcaym.view.menu.components.LevelsList;
import arcaym.view.menu.components.MenuButton;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for the main menu.
 */
public class MenuViewImpl extends AbstractView<MenuController> implements MenuView, ViewComponent<JPanel> {

    private static final String TITLE_IMAGE = Path.of("assets", "title.png").toString();
    private static final String SHOP_BUTTON_TEXT = "OPEN SHOP";
    private static final String CREATE_BUTTON_TEXT = "NEW LEVEL";

    /**
     * Initialize with controller.
     * 
     * @param controller menu controller
     */
    public MenuViewImpl(final MenuController controller) {
        super(controller);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var gap = SwingUtils.getNormalGap(mainPanel);
        final var shopButton = new MenuButton(SHOP_BUTTON_TEXT).build(window);
        final var createButton = new MenuButton(CREATE_BUTTON_TEXT).build(window);
        createButton.addActionListener(
            e -> new CreateLevelDialog(this.controller()::createEditor).show(window, mainPanel)
        );
        shopButton.addActionListener(e -> this.controller().openShop());
        final var buttonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, 0));
        buttonsRow.add(createButton);
        buttonsRow.add(shopButton);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new HorizontalCenteredPanel().build(window, new ImageLabel(TITLE_IMAGE)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new HorizontalCenteredPanel().build(window, buttonsRow));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(new HorizontalCenteredPanel().build(window, new LevelsList(
            this.controller()::getLevels,
            this.controller()::deleteLevel,
            this.controller()::openEditor
        )));
        mainPanel.add(Box.createVerticalStrut(gap));
        return new HorizontalCenteredPanel().build(window, mainPanel);
    }

}
