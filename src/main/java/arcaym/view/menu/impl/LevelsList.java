package arcaym.view.menu.impl;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View of all the saved levels.
 */
public class LevelsList implements ViewComponent<JScrollPane> {

    private final Supplier<List<LevelMetadata>> levelsLoader;
    private final Consumer<LevelMetadata> levelDeleter;
    private final Consumer<LevelMetadata> levelOpener;

    /**
     * Initialize with level opener.
     * 
     * @param levelsLoader levels loader function
     * @param levelDeleter level delete function
     * @param levelOpener level opening function
     */
    public LevelsList(
        final Supplier<List<LevelMetadata>> levelsLoader,
        final Consumer<LevelMetadata> levelDeleter,
        final Consumer<LevelMetadata> levelOpener
    ) {
        this.levelsLoader = Objects.requireNonNull(levelsLoader);
        this.levelDeleter = Objects.requireNonNull(levelDeleter);
        this.levelOpener = Objects.requireNonNull(levelOpener);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        final var scrollPanel = new JScrollPane(mainPanel);
        this.reloadList(mainPanel, scrollPanel, window);
        return scrollPanel;
    }

    private void reloadList(final JPanel mainPanel, final JScrollPane scrollPanel, final WindowInfo window) {
        mainPanel.removeAll();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        final var levels = this.levelsLoader.get();
        levels.stream()
            .sorted((l1, l2) -> l1.levelName().compareTo(l2.levelName()))
            .map(metadata -> new LevelCard(
                metadata, 
                this.levelOpener, 
                m -> {
                    this.levelDeleter.accept(m);
                    this.reloadList(mainPanel, scrollPanel, window);
                }
            ).build(window))
            .flatMap(c -> Stream.of(Box.createVerticalStrut(gap), c))
            .skip(1)
            .forEach(mainPanel::add);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        scrollPanel.setVisible(!levels.isEmpty());
        scrollPanel.revalidate();
        scrollPanel.repaint();
    }

}
