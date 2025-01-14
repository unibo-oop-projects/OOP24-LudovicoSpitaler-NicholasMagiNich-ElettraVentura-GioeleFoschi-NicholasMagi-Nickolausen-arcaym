package arcaym.view.app.impl;

import java.awt.Dimension;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.app.api.MainView;
import arcaym.view.editor.EditorMainView;
import arcaym.view.objects.GameObjectSwingView;

/**
 * Implementation of the main window of the application.
 */
public class MainViewImpl extends JFrame implements MainView {

    private static final long serialVersionUID = 1L;
    private static final Dimension MINIMUM_DIMENSIONS = new Dimension(1366, 768);
    private EditorMainView editor;
    private JPanel game;

    /**
     * Default constructor.
     */
    public MainViewImpl() {
        // Sets the location of the JFrame in the center of the screen
        this.setLocationRelativeTo(null);
        this.setMinimumSize(MINIMUM_DIMENSIONS);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.initEditor();
        this.initGame();
        this.switchToEditor();
        this.setVisible(true);
    }

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
        this.remove(game);
        this.add(editor);
        editor.setVisible(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void switchToGame() {
        initGame();
        this.remove(editor);
        this.add(game);
        game.setVisible(true);
    }

    private void initEditor() {
        this.editor = new EditorMainView(createGameObjects());
        editor.setSize(this.getSize());
        editor.setPreferredSize(this.getSize());
        editor.initView();
    }

    private void initGame() {
        game = new JPanel();
        game.setSize(this.getSize());
        game.setPreferredSize(this.getSize());
        // game.initView();
    }

    /**
     * Debug only!
     * @param args
     */
    public static void main(final String... args) {
        new MainViewImpl();
    }

}
