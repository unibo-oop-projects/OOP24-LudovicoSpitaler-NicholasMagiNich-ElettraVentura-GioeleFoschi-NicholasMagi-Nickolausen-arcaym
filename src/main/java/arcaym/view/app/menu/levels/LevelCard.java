package arcaym.view.app.menu.levels;

import java.awt.BorderLayout;
import java.util.Objects;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

/**
 * View for a {@link LevelMetadata}.
 */
public class LevelCard implements ViewComponent<JButton> {

    private final LevelMetadata metadata;

    /**
     * Initialize with given metadata.
     * 
     * @param metadata level metadata
     */
    public LevelCard(final LevelMetadata metadata) {
        this.metadata = Objects.requireNonNull(metadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton build() {
        final var button = new JButton();
        final var normalGap = SwingUtils.getNormalGap(button);
        final var littleGap = SwingUtils.getLittleGap(button);

        final var nameLabel = new JLabel(this.metadata.levelName());
        SwingUtils.changeFontSize(nameLabel, 4);
        final var infoPanel = new JPanel();
        infoPanel.setBackground(SwingUtils.TRANSPARENT_COLOR);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(new JLabel("ID: " + this.metadata.uuid()));
        infoPanel.add(Box.createVerticalStrut(littleGap));
        infoPanel.add(new JLabel("Size: " + this.metadata.size().x() + "x" + this.metadata.size().y()));
        infoPanel.add(Box.createVerticalStrut(littleGap));
        infoPanel.add(new JLabel("Type: " + this.metadata.type().name()));
        infoPanel.add(Box.createVerticalGlue());

        button.setLayout(new BorderLayout());
        button.add(nameLabel, BorderLayout.WEST);
        button.add(Box.createHorizontalStrut(normalGap), BorderLayout.CENTER);
        button.add(infoPanel, BorderLayout.EAST);

        button.addActionListener(e -> {
            // TODO open new editor from metadata
        });

        return button;
    }

}
