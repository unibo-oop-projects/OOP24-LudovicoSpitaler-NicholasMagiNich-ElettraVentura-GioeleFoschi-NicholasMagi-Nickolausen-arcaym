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
import arcaym.view.app.panels.SwitchablePanel;
import arcaym.view.app.panels.Switcher;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View for a levels panel.
 */
public class LevelsPanel extends SwitchablePanel {

    private final List<LevelMetadata> levels = new MetadataManagerImpl().loadData();

    /**
     * Initialize with given switcher.
     * 
     * @param switcher switcher function
     */
    public LevelsPanel(final Switcher switcher) {
        super(switcher);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
        final var gap = SwingUtils.getNormalGap(mainPanel);
        levels.stream()
            .sorted((l1, l2) -> l1.levelName().compareTo(l2.levelName()))
            .map(LevelCard::new)
            .map(l -> l.build(window))
            .flatMap(c -> Stream.of(Box.createVerticalStrut(gap), c))
            .skip(1)
            .forEach(mainPanel::add);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(gap, gap, gap, gap));
        return new CenteredPanel().build(window, new JScrollPane(mainPanel));
    }

}
