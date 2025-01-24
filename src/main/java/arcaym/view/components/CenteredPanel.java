package arcaym.view.components;

import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.view.api.GeneralSwingView;
import arcaym.view.utils.SwingUtils;

public class CenteredPanel implements GeneralSwingView<JPanel> {

    private final JComponent content;

    public CenteredPanel(final JComponent content) {
        this.content = Objects.requireNonNull(content);
    }

    public CenteredPanel(final GeneralSwingView<?> content) {
        this(content.build());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final var panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
        panel.add(Box.createHorizontalGlue());
        panel.add(this.content);
        panel.add(Box.createHorizontalGlue());
        return panel;
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(new CenteredPanel(new JLabel("Test")));
    }

}
