package arcaym.view.app.impl;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Objects;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorType;
import arcaym.model.editor.saves.LevelMetadata;
import arcaym.view.api.GeneralSwingView;

/**
 * View of a {@link LevelMetadata}.
 */
public class LevelMetadataView implements GeneralSwingView<JButton> {

    private static final float NORMAL_GAP_FACTOR = 1f;
    private static final float LITTLE_GAP_FACTOR = 0.5f;
    private static final float TITLE_FONT_SIZE_FACTOR = 4;
    private static final Color INFO_PANEL_COLOR = new Color(0, 0, 0, 0);

    private final LevelMetadata metadata;

    public LevelMetadataView(final LevelMetadata metadata) {
        this.metadata = Objects.requireNonNull(metadata);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JButton build() {
        final var button = new JButton();
        final var normalGap = Math.round(button.getFont().getSize() * NORMAL_GAP_FACTOR);
        final var littleGap = Math.round(button.getFont().getSize() * LITTLE_GAP_FACTOR);

        final var nameLabel = new JLabel(this.metadata.levelName());
        final var titleFont = nameLabel.getFont();
        nameLabel.setFont(titleFont.deriveFont(titleFont.getSize() * TITLE_FONT_SIZE_FACTOR));

        final var infoPanel = new JPanel();
        infoPanel.setBackground(INFO_PANEL_COLOR);
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
        return button;
    }

    public static void main(String[] args) {
        final var frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        
        final var panel = new JPanel();
        frame.setContentPane(panel);
        panel.setLayout(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(100, 100, 100, 100));
        panel.add(new LevelMetadataView(
            new LevelMetadata("TestLevel", "1234", EditorType.NORMAL, new Position(100, 40))
        ).build(), BorderLayout.CENTER);

        frame.pack();
    }

}
