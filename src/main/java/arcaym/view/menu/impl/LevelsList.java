package arcaym.view.menu.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.editor.saves.MetadataManagerImpl;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View of all the saved levels.
 */
public class LevelsList implements ViewComponent<JScrollPane> {

    private final List<LevelMetadata> levels = new MetadataManagerImpl().loadData();
    private final Consumer<LevelMetadata> levelOpener;

    /**
     * Initialize with level opener.
     * 
     * @param levelOpener level opening function
     */
    public LevelsList(final Consumer<LevelMetadata> levelOpener) {
        this.levelOpener = Objects.requireNonNull(levelOpener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        this.levels.stream()
            .sorted((l1, l2) -> l1.levelName().compareTo(l2.levelName()))
            .map(metadata -> new LevelCard(metadata, this.levelOpener).build(window))
            .flatMap(c -> Stream.of(Box.createVerticalStrut(gap), c))
            .skip(1)
            .forEach(mainPanel::add);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        final var scrollPanel = new JScrollPane(mainPanel);
        scrollPanel.setVisible(!this.levels.isEmpty());
        return scrollPanel;
    }

}
