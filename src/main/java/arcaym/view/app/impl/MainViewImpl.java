package arcaym.view.app.impl;

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
import arcaym.controller.app.api.MainController;
import arcaym.controller.app.impl.MainControllerImpl;
import arcaym.controller.editor.api.EditorController;
import arcaym.controller.game.api.GameController;
import arcaym.controller.menu.api.MenuController;
import arcaym.controller.shop.api.ShopController;
import arcaym.view.app.api.MainView;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.core.impl.ScaleSelector;
import arcaym.view.editor.api.EditorView;
import arcaym.view.editor.impl.EditorMainView;
import arcaym.view.game.api.GameView;
import arcaym.view.game.impl.GameViewImpl;
import arcaym.view.menu.api.MenuView;
import arcaym.view.menu.impl.MenuViewImpl;
import arcaym.view.shop.api.ShopView;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link MainView}.
 */
public class MainViewImpl extends AbstractView<MainController> implements MainView {

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

    /**
     * Debug only!
     * 
     * @param args args
     */
    public static void main(final String... args) {
        new MainViewImpl(new MainControllerImpl());
    }

    private void fillContent(ViewComponent<JPanel> mainContent) {
        final var windowInfo = Optionals.orIllegalState(this.windowInfo, "View has not been built yet!");
        final var window = Optionals.orIllegalState(this.window, "View has not been built yet!");

        final var mainPanel = new JPanel(new BorderLayout());
        final var topRow = new JPanel();
        final var normalGap = SwingUtils.getNormalGap(mainPanel);
        final var littleGap = SwingUtils.getLittleGap(mainPanel);
        topRow.setLayout(new BoxLayout(topRow, BoxLayout.LINE_AXIS));
        topRow.setBorder(BorderFactory.createEmptyBorder(littleGap, normalGap, littleGap, normalGap));
        final var backButton = new JButton(BACK_BUTTON_TEXT);
        backButton.addActionListener(e -> controller().goBack());
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

    @Override
    public MenuView switchToMenu(MenuController controller) {
        final var menuView = new MenuViewImpl(controller);
        fillContent(menuView);
        return menuView;
    }

    @Override
    public EditorView switchToEditor(EditorController controller) {
        final var editorView = new EditorMainView();
        fillContent(editorView);
        return null;
        // return editorView;
    }

    @Override
    public GameView switchToGame(GameController controller) {
        final var gameView = new GameViewImpl(controller);
        fillContent(gameView);
        return gameView;
    }

    @Override
    public ShopView switchToShop(ShopController controller) {
        return null;
    }

    public void init() {
        this.window = Optional.of(new JFrame());
        this.windowInfo = Optional.of(new ScaleSelector().askScale(this.window.get()));
        this.window.get().setVisible(true);
        this.window.get().addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                controller().close();
            }
        });
    }
}
