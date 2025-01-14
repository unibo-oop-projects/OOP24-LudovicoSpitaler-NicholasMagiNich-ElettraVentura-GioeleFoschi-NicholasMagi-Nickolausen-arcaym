package arcaym.view.app.impl;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import arcaym.model.game.core.objects.api.GameObjectCategory;
import arcaym.view.app.api.MainView;
import arcaym.view.editor.EditorMainView;
import arcaym.view.editor.components.api.GeneralSwingView;
import arcaym.view.objects.GameObjectSwingView;

/**
 * Implementation of the main window of the application.
 */
public class MainViewImpl extends JFrame implements MainView, GeneralSwingView {

    private static final long serialVersionUID = 1L;
    private static final Dimension MINIMUM_SCREEN_SIZE = new Dimension(1024, 768);
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private final String WINDOW_TITLE = "Architect of Mayhem";
    private EditorMainView editor;
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
        this.remove(game);
        this.add(editor);
        editor.setVisible(true);
        this.setTitle(WINDOW_TITLE + " - Editing");
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
        this.setTitle(WINDOW_TITLE);
    }

    private void initEditor() {
        this.editor = new EditorMainView(createGameObjects());
        editor.setMinimumSize(MINIMUM_SCREEN_SIZE);
        editor.setSize(screenSize);
        editor.setPreferredSize(screenSize);
        editor.initView();
    }

    private void resizeMainPanels() {
        editor.setSize(this.getSize());
        game.setSize(this.getSize());
    }

    private void initGame() {
        game = new JPanel();
        game.setMinimumSize(MINIMUM_SCREEN_SIZE);
        game.setSize(screenSize);
        game.setPreferredSize(screenSize);
        // game.initView();
    }

    /**
     * Debug only!
     * @param args
     */
    public static void main(final String... args) {
        new MainViewImpl().initView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void initView() {
        // Sets the location of the JFrame in the center of the screen
        this.setLocationRelativeTo(null);
        this.setMinimumSize(MINIMUM_SCREEN_SIZE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.initEditor();
        this.initGame();
        this.switchToEditor();
        this.pack();
        this.setVisible(true);
        this.addComponentListener(new ComponentListener() {

            @Override
            public void componentResized(ComponentEvent e) {
                resizeMainPanels();
            }

            @Override
            public void componentMoved(ComponentEvent e) { }

            @Override
            public void componentShown(ComponentEvent e) { }

            @Override
            public void componentHidden(ComponentEvent e) { }
            
        });
    }

}
