package arcaym.view.app.panels;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.Objects;
import java.util.Optional;
import java.util.Stack;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JButton;
import javax.swing.JPanel;

import arcaym.view.api.ViewComponent;
import arcaym.view.utils.SwingUtils;

public class PanelsSwitcher implements ViewComponent<JPanel> {

    private static final String BACK_BUTTON_TEXT = "BACK";

    private final Stack<Supplier<SwitchablePanel>> panelsHistory = new Stack<>();
    private Optional<SwitchablePanel> currentPanel = Optional.empty();
    private final Function<Consumer<Supplier<SwitchablePanel>>, Supplier<SwitchablePanel>> rootProvider;

    public PanelsSwitcher(
        final Function<Consumer<Supplier<SwitchablePanel>>, Supplier<SwitchablePanel>> rootProvider
    ) {
        this.rootProvider = Objects.requireNonNull(rootProvider);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build() {
        final var mainPanel = new JPanel(new BorderLayout());
        final var gap = SwingUtils.getNormalGap(mainPanel);
        final var topRow = new JPanel(new FlowLayout(FlowLayout.LEADING, gap, gap));
        final var backButton = new JButton(BACK_BUTTON_TEXT);
        topRow.add(backButton);
        backButton.addActionListener(e -> this.goToPrevious(mainPanel, topRow));
        this.addPanel(
            this.rootProvider.apply(s -> this.addPanel(s, mainPanel, topRow)),
            mainPanel, 
            topRow
        );
        return mainPanel;
    }

    private boolean canGoBack() {
        return this.panelsHistory.size() > 1;
    }

    private void goToPrevious(final JPanel mainPanel, final JPanel topRow) {
        if (this.canGoBack()) {
            this.panelsHistory.pop();
            this.setCurrentPanel(this.panelsHistory.peek(), mainPanel, topRow);
        }
    }

    private void addPanel(
        final Supplier<SwitchablePanel> panelSupplier,
        final JPanel mainPanel, 
        final JPanel topRow
    ) {
        this.panelsHistory.add(panelSupplier);
        this.setCurrentPanel(panelSupplier, mainPanel, topRow);
    }

    private void setCurrentPanel(
        final Supplier<SwitchablePanel> panelSupplier,
        final JPanel mainPanel, 
        final JPanel topRow
    ) {
        this.currentPanel.ifPresent(SwitchablePanel::onClose);
        this.currentPanel = Optional.of(panelSupplier.get());
        mainPanel.removeAll();
        if (this.canGoBack()) {
            mainPanel.add(topRow, BorderLayout.NORTH);
        }
        this.currentPanel.ifPresent(p -> mainPanel.add(p.build(), BorderLayout.CENTER));
        mainPanel.revalidate();
        mainPanel.repaint();
    }

}
