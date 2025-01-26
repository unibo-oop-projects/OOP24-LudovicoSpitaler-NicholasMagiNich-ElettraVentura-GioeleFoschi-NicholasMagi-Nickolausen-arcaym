package arcaym.view.app.menu;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;
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
        final var mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(new ImageLabel(TITLE_IMAGE).build(), BorderLayout.CENTER);
        return mainPanel;
    }

    /**
     * TODO remove.
     * @param args foiaeuighiouweahiuogfea
     */
    public static void main(final String[] args) {
        SwingUtils.testComponent(new MainMenu().build());
    }

}
