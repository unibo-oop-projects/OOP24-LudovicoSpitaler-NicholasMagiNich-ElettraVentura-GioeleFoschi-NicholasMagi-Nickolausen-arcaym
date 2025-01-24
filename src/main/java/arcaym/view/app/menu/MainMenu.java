package arcaym.view.app.menu;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

public class MainMenu implements ViewComponent<JPanel> {

    private static final String TITLE_IMAGE = "title.png";

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final var mainPanel = new JPanel();
        mainPanel.add(new JLabel(new ImageIcon(SwingUtils.getResource(TITLE_IMAGE))));
        return mainPanel;
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(new MainMenu().build());
    }

}
