package arcaym.view.app.menu.levels;

import java.util.List;
import java.util.stream.Stream;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import arcaym.controller.editor.saves.LevelMetadata;
import arcaym.controller.editor.saves.MetadataManagerImpl;
import arcaym.view.core.api.ScreenInfo;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

/**
 * Scroll list with a {@link LevelCard} for each saved level.
 */
public class LevelsList implements ViewComponent<JScrollPane> {

    private final List<LevelMetadata> levels = new MetadataManagerImpl().loadData();

    /**
     * {@inheritDoc}
     */
    @Override
    public JScrollPane build(final ScreenInfo screenInfo) {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        levels.stream()
            .sorted((l1, l2) -> l1.levelName().compareTo(l2.levelName()))
            .map(LevelCard::new)
            .map(l -> l.build(screenInfo))
            .flatMap(c -> Stream.of(Box.createVerticalStrut(gap), c))
            .skip(1)
            .forEach(mainPanel::add);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        return new JScrollPane(mainPanel);
    }

}
