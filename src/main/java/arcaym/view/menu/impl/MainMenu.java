package arcaym.view.menu.impl;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arcaym.view.app.impl.PanelsSwitcher;
import arcaym.view.app.impl.SwitchablePanel;
import arcaym.view.app.impl.Switcher;
import arcaym.view.components.CenteredPanel;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.menu.api.MenuView;
import arcaym.view.utils.SwingUtils;

/**
 * View for the main menu.
 */
public class MainMenu extends SwitchablePanel implements MenuView {

    private static final String TITLE_IMAGE = "title.png";
    private static final String LEVELS_BUTTON_TEXT = "LOAD LEVELS";
    private static final String SHOP_BUTTON_TEXT = "OPEN SHOP";

    /**
     * Initialize with given switcher.
     * 
     * @param switcher switcher function
     */
    public MainMenu(final Switcher switcher) {
        super(switcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var levelsButton = new MenuButton(LEVELS_BUTTON_TEXT).build(window);
        final var shopButton = new MenuButton(SHOP_BUTTON_TEXT).build(window);
        levelsButton.addActionListener(e -> this.switcher().switchTo(() -> new LevelsPanel(this.switcher())));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new CenteredPanel().build(window, new ImageLabel(TITLE_IMAGE)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CenteredPanel().build(window, levelsButton));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(new CenteredPanel().build(window, shopButton));
        mainPanel.add(Box.createVerticalGlue());
        return new CenteredPanel().build(window, mainPanel);
    }

    /**
     * TODO remove.
     * @param args foiaeuighiouweahiuogfea
     */
    public static void main(final String[] args) {
        SwingUtils.testComponent((window, frame) -> new PanelsSwitcher(
            switcher -> () -> new MainMenu(switcher),
            () -> SwingUtils.closeFrame(frame)
        ).build(window));
    }

}
