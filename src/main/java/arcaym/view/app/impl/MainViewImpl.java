package arcaym.view.app.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.app.api.MainView;
import arcaym.view.editor.EditorMainView;
import arcaym.view.objects.GameObjectSwingView;

/**
 * Implementation of the main window of the application.
 */
public class MainViewImpl implements MainView {

    private static final Dimension MINIMUM_SCREEN_SIZE = new Dimension(1024, 768);
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final String WINDOW_TITLE = "Architect of Mayhem";
    private final JFrame mainFrame = new JFrame();
    private JPanel editor;
    private JPanel game;

    private Map<GameObjectCategory, Set<GameObjectSwingView>> createGameObjects() {
        final var obstaclesSet = Set.of(
            new GameObjectSwingView(GameObjectCategory.OBSTACLE, "static_obstacle.png")
        );
        final var goalSet = Set.of(
            new GameObjectSwingView(GameObjectCategory.GOAL, "coin.png")
        );
        final var playerSet = Set.of(
            new GameObjectSwingView(GameObjectCategory.PLAYER, "coin.png")
        );
        final var collectableSet = Set.of(
            new GameObjectSwingView(GameObjectCategory.COLLECTABLE, "coin.png")
        );
        
        final var blockSet = new HashSet<GameObjectSwingView>();
        for (int i = 0; i < 30; i++) {
            blockSet.add(new GameObjectSwingView(GameObjectCategory.BLOCK, "coin.png"));
        }

        return Map.of(
            GameObjectCategory.OBSTACLE, obstaclesSet,
            GameObjectCategory.GOAL, goalSet,
            GameObjectCategory.PLAYER, playerSet,
            GameObjectCategory.COLLECTABLE, collectableSet,
            GameObjectCategory.BLOCK, blockSet
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToEditor() {
        initEditor();
        mainFrame.remove(game);
        mainFrame.add(editor);
        editor.setVisible(true);
        mainFrame.setTitle(WINDOW_TITLE + " - Editing");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToGame() {
        initGame();
        mainFrame.remove(editor);
        mainFrame.add(game);
        game.setVisible(true);
        mainFrame.setTitle(WINDOW_TITLE);
    }

    private void initEditor() {
        editor = new EditorMainView(createGameObjects()).build();
        editor.setMinimumSize(MINIMUM_SCREEN_SIZE);
        editor.setSize(screenSize);
        editor.setPreferredSize(screenSize);
        mainFrame.add(editor);
    }

    private void initGame() {
        game = new JPanel();
        game.setMinimumSize(MINIMUM_SCREEN_SIZE);
        game.setSize(screenSize);
        game.setPreferredSize(screenSize);
        // mainFrame.add(game.build());
    }

    /**
     * Debug only!
     * @param args
     */
    public static void main(final String... args) {
        new MainViewImpl();
    }

    /**
     * {@inheritDoc}
     */
    public MainViewImpl() {
        // Sets the location of the JFrame in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setMinimumSize(MINIMUM_SCREEN_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.initEditor();
        this.initGame();
        this.switchToEditor();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
