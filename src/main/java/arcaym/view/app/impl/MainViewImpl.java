package arcaym.view.app.impl;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;

import arcaym.view.app.api.MainView;

/**
 * Implementation of the main window of the application.
 */
public class MainViewImpl implements MainView {

    private static final Dimension MINIMUM_SCREEN_SIZE = new Dimension(1024, 768);
    private static Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final String WINDOW_TITLE = "Architect of Mayhem";
    private final JFrame mainFrame = new JFrame();
    private JPanel editor;
    private JPanel game;

    /**
     * {@inheritDoc}
     */
    @Override
    public final void switchToEditor() {
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
        // editor = new EditorMainView().build();
        // editor.setMinimumSize(MINIMUM_SCREEN_SIZE);
        // editor.setSize(screenSize);
        // editor.setPreferredSize(screenSize);
        // mainFrame.add(editor);
        editor = new JPanel();
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
     * 
     * @param args
     */
    public static void main(final String... args) {
        new MainViewImpl();
    }

    /**
     * Constructor of the main view.
     */
    public MainViewImpl() {
        // Sets the location of the JFrame in the center of the screen
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setMinimumSize(MINIMUM_SCREEN_SIZE);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        // this.initEditor();
        // this.initGame();
        // this.switchToEditor();
        mainFrame.pack();
        mainFrame.setVisible(true);
    }

}
