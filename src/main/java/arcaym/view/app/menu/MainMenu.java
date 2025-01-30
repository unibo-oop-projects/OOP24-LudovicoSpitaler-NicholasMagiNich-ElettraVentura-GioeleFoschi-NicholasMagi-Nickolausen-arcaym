package arcaym.view.app.menu;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arcaym.view.app.menu.levels.LevelsList;
import arcaym.view.components.CenteredPanel;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

/**
 * View for the main menu.
 */
public class MainMenu implements ViewComponent<JPanel> {

    private static final String TITLE_IMAGE = "title.png";

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var loadLevelsButton = new MenuButton("Load levels").build(window);
        final var openShopButton = new MenuButton("Open shop").build(window);
        final var gap = SwingUtils.getNormalGap(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new CenteredPanel().build(window, new ImageLabel(TITLE_IMAGE)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CenteredPanel().build(window, loadLevelsButton));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(new CenteredPanel().build(window, openShopButton));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(new CenteredPanel().build(window, new LevelsList()));
        mainPanel.add(Box.createVerticalStrut(gap));
        return new CenteredPanel().build(window, mainPanel);
    }

}
