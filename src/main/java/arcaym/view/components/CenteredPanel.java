package arcaym.view.components;

import java.util.Optional;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.view.api.ParentComponent;
import arcaym.view.utils.SwingUtils;

/**
 * Blank panel that centers it's content without stretching it.
 */
public class CenteredPanel implements ParentComponent<JPanel> {

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final Optional<JComponent> childComponent) {
        final var panel = new JPanel();
        childComponent.ifPresent(c -> {
            panel.setLayout(new BoxLayout(panel, BoxLayout.LINE_AXIS));
            panel.add(Box.createHorizontalGlue());
            panel.add(c);
            panel.add(Box.createHorizontalGlue());
        });
        return panel;
    }

    public static void main(String[] args) {
        SwingUtils.testComponent(new CenteredPanel().build(new JLabel("Test")));
    }

}
