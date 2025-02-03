package arcaym.view.game.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import arcaym.common.geometry.impl.Rectangle;
import arcaym.controller.game.api.GameController;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.app.impl.AbstractView;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.game.api.GameView;
import arcaym.view.objects.GameObjectView;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link GameView}.
 */
public class GameViewImpl extends AbstractView<GameController> implements GameView, ViewComponent<JPanel> {
    private static final int SCALE = 2;
    private static final int KEY_UP = KeyEvent.VK_W;
    private static final int KEY_DOWN = KeyEvent.VK_S;
    private static final int KEY_LEFT = KeyEvent.VK_A;
    private static final int KEY_RIGHT = KeyEvent.VK_D;
    private Optional<Consumer<JPanel>> setKeyBindings = Optional.empty();
    private Optional<BiConsumer<JPanel, JLabel>> setGameEventReaction = Optional.empty();
    private Optional<GamePanel> gamePanel = Optional.empty();
    private final Rectangle boundaries;
    private final ConcurrentMap<GameObjectInfo, Image> gameMap = new ConcurrentHashMap<>();

    /**
     * Base constructor for GameViewImpl.
     * 
     * @param controller game controller
     */
    public GameViewImpl(final GameController controller) {
        super(controller);
        this.boundaries = this.controller().getGameState().boundaries();
    }

    /**
     * "The field arcaym.view.game.impl.GamePanel.gameMap is transient but isn't set
     * by deserialization In GamePanel.java"
     * Due to the quoted spotbugs error, we implemented the game panel class
     * directly in the game view implementation,
     * as a private class. In this way, the fields in GamePanel do not have
     * serialization problem
     */
    private final class GamePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        /**
         * {@inheritDoc}
         */
        @Override
        protected void paintComponent(final Graphics g) {
            super.paintComponent(g);
            final double scale = calculateScale();
            final Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
            gameMap.entrySet().stream().forEach(entry -> {
                g2d.drawImage(entry.getValue(),
                        Double.valueOf(entry.getKey().boundaries().northWest().x() * scale).intValue(),
                        Double.valueOf(entry.getKey().boundaries().northWest().y() * scale).intValue(),
                        Double.valueOf(entry.getKey().boundaries().base() * scale).intValue(),
                        Double.valueOf(entry.getKey().boundaries().height() * scale).intValue(), null);
            });
            g2d.dispose();
        }

        private double calculateScale() {
            return Math.min(
                    this.getSize().getHeight() / boundaries.height(),
                    this.getSize().getWidth() / boundaries.base());
        }

        /**
         * Creates new object in the game.
         * 
         * @param gameObject
         */
        public void createObject(final GameObjectInfo gameObject) {
            gameMap.put(gameObject,new GameObjectView(gameObject.type()).getImage().get());
            this.repaint();
        }

        /**
         * Updates new object in the game.
         * 
         * @param gameObject
         */
        public void updateObject(final GameObjectInfo gameObject) {
            this.repaint();
        }

        /**
         * Destroys new object in the game.
         * 
         * @param gameObject
         */
        public void destroyObject(final GameObjectInfo gameObject) {
            gameMap.remove(gameObject);
            this.repaint();
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        // These operations must be setted before the build starts
        if (setGameEventReaction.isEmpty() || setKeyBindings.isEmpty()) {
            throw new IllegalStateException("Operation not initialized.");
        }
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        // The header contains the game score
        final JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        // The gameContentPanel contains the game view (with the game objects)
        final GamePanel gameContentPanel = new GamePanel();
        gamePanel = Optional.of(gameContentPanel);
        // accepts the key bindings setup on the main panel
        setKeyBindings.get().accept(mainPanel);
        // score label
        final JLabel score = new JLabel();
        SwingUtils.changeFontSize(score, SCALE);
        scoreUpdateLabel(score);
        setGameEventReaction.get().accept(gameContentPanel, score);
        header.add(Box.createHorizontalStrut(SwingUtils.getNormalGap(header)));
        header.add(score);
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(gameContentPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private void scoreUpdateLabel(final JLabel score) {
        score.setText("SCORE : " + this.controller().getGameState().score().getValue());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber,
            final GameStateInfo gameState) {
        // This method will be called before build().
        setGameEventReaction = Optional.of((gamePanel, scoreLabel) -> {
            eventsSubscriber.registerCallback(GameEvent.GAME_OVER, (gameEvent) -> {
                JOptionPane.showMessageDialog(gamePanel, gameEvent.name());
            });
            eventsSubscriber.registerCallback(GameEvent.DECREMENT_SCORE, (gameEvent) -> {
                scoreUpdateLabel(scoreLabel);
            });
            eventsSubscriber.registerCallback(GameEvent.INCREMENT_SCORE, (gameEvent) -> {
                scoreUpdateLabel(scoreLabel);
            });
            eventsSubscriber.registerCallback(GameEvent.VICTORY, (gameEvent) -> {
                JOptionPane.showMessageDialog(gamePanel, gameEvent.name());
            });
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(final EventsScheduler<InputEvent> eventsScheduler) {
        // This method will be called before build().
        setKeyBindings = Optional.of((out) -> {
            bindKey(InputType.UP, KEY_UP, eventsScheduler, out);
            bindKey(InputType.DOWN, KEY_DOWN, eventsScheduler, out);
            bindKey(InputType.LEFT, KEY_LEFT, eventsScheduler, out);
            bindKey(InputType.RIGHT, KEY_RIGHT, eventsScheduler, out);
        });
    }

    private void bindKey(final InputType type, final int keyCode, final EventsScheduler<InputEvent> eventsScheduler,
            final JPanel out) {
        final InputEvent nonDropEvent = new InputEvent(type, false);
        final String nonDropKey = type.name() + "_PRESSED";
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,
                0, false), nonDropKey);
        out.getActionMap().put(nonDropKey, new AbstractAction() {

            @Override
            public void actionPerformed(final ActionEvent arg) {
                eventsScheduler.scheduleEvent(nonDropEvent);
            }

        });

        final InputEvent dropEvent = new InputEvent(type, true);
        final String dropKey = type.name() + "_RELEASED";
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,
                0, true), dropKey);
        out.getActionMap().put(dropKey, new AbstractAction() {

            @Override
            public void actionPerformed(final ActionEvent arg) {
                eventsScheduler.scheduleEvent(dropEvent);
            }

        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyObject(final GameObjectInfo gameObject) {
        this.gamePanel.ifPresent((panel) -> panel.destroyObject(gameObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject) {
        this.gamePanel.ifPresent((panel) -> panel.createObject(gameObject));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        this.gamePanel.ifPresent((panel) -> panel.updateObject(gameObject));
    }

}
