package arcaym.view.menu.components;

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
import arcaym.view.core.ViewComponent;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a {@link LevelMetadata}.
 */
public class LevelCard implements ViewComponent<JPanel> {

    private static final String KEY_DIVISOR = ": ";
    private static final String DELETE_BUTTON_TEXT = "DELETE";

    private final LevelMetadata metadata;
    private final Consumer<LevelMetadata> levelOpener;
    private final Consumer<LevelMetadata> levelDeleter;

    /**
     * Initialize with given metadata.
     * 
     * @param metadata level metadata
     * @param levelOpener level opener function
     * @param levelDeleter level delete function
     */
    public LevelCard(
        final LevelMetadata metadata, 
        final Consumer<LevelMetadata> levelOpener,
        final Consumer<LevelMetadata> levelDeleter
    ) {
        this.metadata = Objects.requireNonNull(metadata);
        this.levelOpener = Objects.requireNonNull(levelOpener);
        this.levelDeleter = Objects.requireNonNull(levelDeleter);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var normalGap = SwingUtils.getNormalGap(mainPanel);
        final var littleGap = SwingUtils.getLittleGap(mainPanel);
        mainPanel.setLayout(new BorderLayout(littleGap, littleGap));
        final var levelButton = new JButton();
        final var nameLabel = new JLabel(this.metadata.levelName());
        SwingUtils.changeFontSize(nameLabel, 4);
        mainPanel.setOpaque(false);
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
        final var deleteButton = new JButton(DELETE_BUTTON_TEXT);
        deleteButton.addActionListener(e -> this.levelDeleter.accept(this.metadata));
        levelButton.setLayout(new BorderLayout());
        levelButton.add(nameLabel, BorderLayout.WEST);
        levelButton.add(Box.createHorizontalStrut(normalGap), BorderLayout.CENTER);
        levelButton.add(infoPanel, BorderLayout.EAST);
        levelButton.addActionListener(e -> this.levelOpener.accept(this.metadata));
        mainPanel.add(levelButton, BorderLayout.CENTER);
        mainPanel.add(deleteButton, BorderLayout.EAST);
        return mainPanel;
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
