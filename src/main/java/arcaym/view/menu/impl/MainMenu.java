package arcaym.view.menu.impl;

import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arcaym.view.components.CenteredPanel;
import arcaym.view.components.ImageLabel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for the main menu.
 */
public class MainMenu implements ViewComponent<JPanel> {

    private static final String TITLE_IMAGE = "title.png";
    private static final String SHOP_BUTTON_TEXT = "OPEN SHOP";
    private static final String CREATE_BUTTON_TEXT = "NEW LEVEL";

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var gap = SwingUtils.getNormalGap(mainPanel);
        final var shopButton = new MenuButton(SHOP_BUTTON_TEXT).build(window);
        final var createButton = new MenuButton(CREATE_BUTTON_TEXT).build(window);
        createButton.addActionListener(e -> new CreateLevelDialog().show(window, mainPanel));
        final var buttonsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, gap, 0));
        buttonsRow.add(createButton);
        buttonsRow.add(shopButton);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new CenteredPanel().build(window, new ImageLabel(TITLE_IMAGE)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CenteredPanel().build(window, buttonsRow));
        mainPanel.add(Box.createVerticalStrut(gap));
        mainPanel.add(new CenteredPanel().build(window, new LevelsList()));
        mainPanel.add(Box.createVerticalStrut(gap));
        return new CenteredPanel().build(window, mainPanel);
    }

    /**
     * TODO remove.
     * @param args foiaeuighiouweahiuogfea
     */
    public static void main(final String[] args) {
        SwingUtils.testComponent(new MainMenu()::build);
    }

}
