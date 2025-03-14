package arcaym.view.app;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Optional;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import arcaym.common.utils.Optionals;
import arcaym.controller.app.MainController;
import arcaym.controller.editor.EditorController;
import arcaym.controller.game.GameController;
import arcaym.controller.menu.MenuController;
import arcaym.controller.shop.ShopController;
import arcaym.view.core.ViewComponent;
import arcaym.view.editor.EditorMainView;
import arcaym.view.editor.EditorView;
import arcaym.view.game.GameView;
import arcaym.view.game.GameViewImpl;
import arcaym.view.menu.MenuView;
import arcaym.view.menu.MenuViewImpl;
import arcaym.view.scaling.ScaleSelector;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.shop.ShopView;
import arcaym.view.shop.ShopViewImpl;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link MainView}.
 */
public class MainViewImpl extends AbstractView<MainController> implements MainView {

    private static final String APPLICATION_TITLE = "Architect of Mayhem";
    private static final String BACK_BUTTON_TEXT = "BACK";
    private static final String CLOSE_BUTTON_TEXT = "CLOSE";
    private Optional<JFrame> window = Optional.empty();
    private Optional<WindowInfo> windowInfo = Optional.empty();

    /**
     * Default constructor.
     * 
     * @param controller main controller
     */
    public MainViewImpl(final MainController controller) {
        super(controller);
    }

    private void fillContent(final ViewComponent<JPanel> mainContent) {
        final var windowInfo = Optionals.orIllegalState(this.windowInfo, "View has not been built yet!");
        final var window = Optionals.orIllegalState(this.window, "View has not been built yet!");

        final var mainPanel = new JPanel(new BorderLayout());
        final var topRow = new JPanel();
        final var normalGap = SwingUtils.getNormalGap(mainPanel);
        final var littleGap = SwingUtils.getLittleGap(mainPanel);
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
        topRow.setBorder(BorderFactory.createEmptyBorder(littleGap, normalGap, littleGap, normalGap));
        final var backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(e -> this.controller().goBack());
        final var closeButton = new JButton(CLOSE_BUTTON_TEXT);
        closeButton.addActionListener(e -> this.controller().close());
        if (this.controller().canGoBack()) {
            topRow.add(backButton);
        }
        if (windowInfo.isFullscreen()) {
            topRow.add(Box.createHorizontalGlue());
            topRow.add(closeButton);
        }
        mainPanel.add(topRow, BorderLayout.NORTH);
        mainPanel.add(mainContent.build(windowInfo), BorderLayout.CENTER);
        window.setContentPane(mainPanel);
        window.revalidate();
        window.repaint();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public MenuView switchToMenu(final MenuController controller) {
        final var menuView = new MenuViewImpl(controller);
        fillContent(menuView);
        return menuView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public EditorView switchToEditor(final EditorController controller) {
        final var editorView = new EditorMainView(controller);
        fillContent(editorView);
        return editorView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GameView switchToGame(final GameController controller) {
        final var gameView = new GameViewImpl(controller);
        fillContent(gameView);
        return gameView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ShopView switchToShop(final ShopController controller) {
        final var shopView = new ShopViewImpl(controller);
        fillContent(shopView);
        return shopView;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean init() {
        final var window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setTitle(APPLICATION_TITLE);
        final var windowInfo = new ScaleSelector().askScale(window);
        if (windowInfo.isEmpty()) {
            SwingUtils.closeFrame(window);
            return false;
        }
        window.setVisible(true);
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                controller().close();
            }
        });
        this.window = Optional.of(window);
        this.windowInfo = windowInfo;
        return true;
    }
}
