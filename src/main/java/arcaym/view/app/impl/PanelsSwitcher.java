package arcaym.view.app.impl;

import java.awt.BorderLayout;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * View of a panel with content-switching capabilities.
 */
public class PanelsSwitcher implements ViewComponent<JPanel> {

    private static final String BACK_BUTTON_TEXT = "BACK";
    private static final String CLOSE_BUTTON_TEXT = "CLOSE";

    private final Deque<Supplier<SwitchablePanel>> panelsHistory = new LinkedList<>();
    private Optional<SwitchablePanel> currentPanel = Optional.empty();
    private final Function<Switcher, Supplier<SwitchablePanel>> rootProvider;
    private final Runnable closeOperation;

    /**
     * Initialize with given root panel.
     * 
     * @param rootProvider root provider
     * @param closeOperation operation to run on close
     */
    public PanelsSwitcher(
        final Function<Switcher, Supplier<SwitchablePanel>> rootProvider,
        final Runnable closeOperation
    ) {
        this.rootProvider = Objects.requireNonNull(rootProvider);
        this.closeOperation = Objects.requireNonNull(closeOperation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final var mainPanel = new JPanel(new BorderLayout());
        this.addPanel(
            window,
            this.rootProvider.apply(s -> this.addPanel(window, s, mainPanel)),
            mainPanel
        );
        return mainPanel;
    }

    private boolean canGoBack() {
        return this.panelsHistory.size() > 1;
    }

    private void goToPrevious(final WindowInfo window, final JPanel mainPanel) {
        if (this.canGoBack()) {
            this.panelsHistory.removeLast();
            this.setCurrentPanel(window, this.panelsHistory.peekLast(), mainPanel);
        }
    }

    private void addPanel(
        final WindowInfo window,
        final Supplier<SwitchablePanel> panelSupplier,
        final JPanel mainPanel
    ) {
        this.panelsHistory.addLast(panelSupplier);
        this.setCurrentPanel(window, panelSupplier, mainPanel);
    }

    private void setCurrentPanel(
        final WindowInfo window,
        final Supplier<SwitchablePanel> panelSupplier,
        final JPanel mainPanel
    ) {
        this.currentPanel.ifPresent(SwitchablePanel::close);
        this.currentPanel = Optional.of(panelSupplier.get());
        mainPanel.removeAll();
        final var normalGap = SwingUtils.getNormalGap(mainPanel);
        final var littleGap = SwingUtils.getLittleGap(mainPanel);
        final var topRow = new JPanel();
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
        topRow.setBorder(BorderFactory.createEmptyBorder(littleGap, normalGap, littleGap, normalGap));
        final var backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(e -> this.goToPrevious(window, mainPanel));
        final var closeButton = new JButton(CLOSE_BUTTON_TEXT);
        closeButton.addActionListener(e -> this.closeOperation.run());
        if (this.canGoBack()) {
            topRow.add(backButton);
        }
        if (window.isFullscreen()) {
            topRow.add(Box.createHorizontalGlue());
            topRow.add(closeButton);
            // topRow.add(Box.createHorizontalStrut(gap));
        }
        mainPanel.add(topRow, BorderLayout.NORTH);
        this.currentPanel.ifPresent(p -> mainPanel.add(p.build(window), BorderLayout.CENTER));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
