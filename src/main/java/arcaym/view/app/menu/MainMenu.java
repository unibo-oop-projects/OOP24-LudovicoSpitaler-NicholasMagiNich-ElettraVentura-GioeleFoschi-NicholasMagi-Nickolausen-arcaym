package arcaym.view.app.menu;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;
import arcaym.view.components.CenteredPanel;
import arcaym.view.components.ImageLabel;
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
    public JPanel build() {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        mainPanel.add(new CenteredPanel().build(new ImageLabel(TITLE_IMAGE)));
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(new CenteredPanel().build(new MenuButton("Load levels")));
        mainPanel.add(Box.createVerticalStrut(SwingUtils.getNormalGap(mainPanel)));
        mainPanel.add(new CenteredPanel().build(new MenuButton("Open shop")));
        mainPanel.add(Box.createVerticalGlue());
        return new CenteredPanel().build(mainPanel);
    }

    /**
     * TODO remove.
     * @param args foiaeuighiouweahiuogfea
     */
    public static void main(final String[] args) {
        SwingUtils.testComponent(new MainMenu().build());
    }

}
