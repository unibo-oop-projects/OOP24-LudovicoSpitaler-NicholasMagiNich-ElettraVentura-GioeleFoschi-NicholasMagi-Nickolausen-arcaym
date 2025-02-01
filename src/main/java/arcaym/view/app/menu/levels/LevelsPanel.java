package arcaym.view.app.menu.levels;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import arcaym.view.app.menu.MenuButton;
import arcaym.view.app.panels.PanelsSwitcher;
import arcaym.view.app.panels.SwitchablePanel;
import arcaym.view.app.panels.Switcher;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a levels panel.
 */
public class LevelsPanel extends SwitchablePanel {

    private static final String CREATE_BUTTON_TEXT = "CREATE NEW LEVEL";

    /**
     * Initialize with given switcher.
     * 
     * @param switcher switcher function
     */
    public LevelsPanel(final Switcher switcher) {
        super(switcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var gap = SwingUtils.getNormalGap(mainPanel);
        mainPanel.setLayout(new BorderLayout(gap, gap));
        final var createButton = new MenuButton(CREATE_BUTTON_TEXT).build(window);
        createButton.addActionListener(e -> new CreateLevelDialog().show(window, mainPanel));
        mainPanel.add(new CenteredPanel().build(window, createButton), BorderLayout.NORTH);
        mainPanel.add(new LevelsList().build(window), BorderLayout.CENTER);
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtils.testComponent((window, frame) -> new PanelsSwitcher(
            switcher -> () -> new LevelsPanel(switcher),
            () -> SwingUtils.closeFrame(frame)
        ).build(window));
    }

}
