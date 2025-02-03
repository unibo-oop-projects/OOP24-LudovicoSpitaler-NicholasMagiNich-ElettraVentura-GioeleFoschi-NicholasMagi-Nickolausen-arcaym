package arcaym.view.menu.impl;

import java.awt.BorderLayout;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a {@link LevelMetadata}.
 */
public class LevelCard implements ViewComponent<JButton> {

    private static final String KEY_DIVISOR = ": ";

    private final LevelMetadata metadata;
    private final Consumer<LevelMetadata> levelOpener;

    /**
     * Initialize with given metadata.
     * 
     * @param metadata level metadata
     * @param levelOpener level opener function
     */
    public LevelCard(final LevelMetadata metadata, final Consumer<LevelMetadata> levelOpener) {
        this.metadata = Objects.requireNonNull(metadata);
        this.levelOpener = Objects.requireNonNull(levelOpener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton build(final WindowInfo window) {
        final var button = new JButton();
        final var normalGap = SwingUtils.getNormalGap(button);
        final var littleGap = SwingUtils.getLittleGap(button);

        final var nameLabel = new JLabel(this.metadata.levelName());
        SwingUtils.changeFontSize(nameLabel, 4);
        final var infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.PAGE_AXIS));
        infoPanel.add(Box.createVerticalGlue());
        infoPanel.add(this.buildLine("ID", this.metadata.uuid()));
        infoPanel.add(Box.createVerticalStrut(littleGap));
        infoPanel.add(this.buildLine("Size", "x", this.metadata.size().x(), this.metadata.size().y()));
        infoPanel.add(Box.createVerticalStrut(littleGap));
        infoPanel.add(this.buildLine("Type", this.metadata.type().name()));
        infoPanel.add(Box.createVerticalGlue());

        button.setLayout(new BorderLayout());
        button.add(nameLabel, BorderLayout.WEST);
        button.add(Box.createHorizontalStrut(normalGap), BorderLayout.CENTER);
        button.add(infoPanel, BorderLayout.EAST);
        button.addActionListener(e -> this.levelOpener.accept(this.metadata));

        return button;
    }

    private JLabel buildLine(final String key, final Object value) {
        return this.buildLine(key, "", value);
    }

    private JLabel buildLine(final String key, final String valuesDivisor, final Object... values) {
        return new JLabel(
            new StringBuilder(key)
                .append(KEY_DIVISOR)
                .append(String.join(valuesDivisor, Stream.of(values).map(String::valueOf).toList()))
                .toString()
        );
    }

}
