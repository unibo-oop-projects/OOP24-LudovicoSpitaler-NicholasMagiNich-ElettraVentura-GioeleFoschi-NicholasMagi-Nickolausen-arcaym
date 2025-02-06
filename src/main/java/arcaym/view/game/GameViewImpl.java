package arcaym.view.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Comparator;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import arcaym.common.geometry.Point;
import arcaym.common.geometry.Rectangle;
import arcaym.common.utils.Optionals;
import arcaym.controller.game.GameController;
import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.core.objects.GameObjectInfo;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.events.InputEvent;
import arcaym.model.game.events.InputType;
import arcaym.view.app.AbstractView;
import arcaym.view.core.ViewComponent;
import arcaym.view.objects.GameObjectView;
import arcaym.view.scaling.WindowInfo;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link GameView}.
 */
public class GameViewImpl extends AbstractView<GameController> implements GameView, ViewComponent<JPanel> {
    private static final String SCORE = "SCORE : ";
    private static final int LABELS_SCALE = 2;
    private static final int KEY_UP = KeyEvent.VK_W;
    private static final int KEY_DOWN = KeyEvent.VK_S;
    private static final int KEY_LEFT = KeyEvent.VK_A;
    private static final int KEY_RIGHT = KeyEvent.VK_D;
    private Optional<Consumer<EventsScheduler<InputEvent>>> setKeyBindings = Optional.empty();
    private Optional<Consumer<EventsSubscriber<GameEvent>>> setGameEventReaction = Optional.empty();
    private Optional<GamePanel> gamePanel = Optional.empty();
    private final Rectangle boundaries;
    private final ConcurrentMap<GameObjectInfo, GameObjectRepresentation> gameMap = new ConcurrentHashMap<>();

    private record GameObjectRepresentation(Image image, int zIndex) { }

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
     * serialization problems
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
            final var pivot = Point.of(
                (this.getSize().getWidth() - (boundaries.base() * scale)) / 2,
                (this.getSize().getHeight() - (boundaries.height() * scale)) / 2
            );
            gameMap.entrySet().stream().sorted(Comparator.comparingInt(entry -> entry.getValue().zIndex)).forEach(entry -> {
                final var objectBoundaries = entry.getKey().boundaries();
                g2d.drawImage(
                    entry.getValue().image(),
                    Double.valueOf((objectBoundaries.northWest().x() * scale) + pivot.x()).intValue(),
                    Double.valueOf((objectBoundaries.northWest().y() * scale) + pivot.y()).intValue(),
                    Double.valueOf(objectBoundaries.base() * scale).intValue(),
                    Double.valueOf(objectBoundaries.height() * scale).intValue(), 
                    null
                );
            });
            g2d.dispose();
        }

        private double calculateScale() {
            return Math.min(
                this.getSize().getHeight() / boundaries.height(),
                this.getSize().getWidth() / boundaries.base()
            );
        }

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
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
        setKeyBindings = Optional.of((eventsScheduler) -> {
            bindKey(InputType.UP, KEY_UP, eventsScheduler, gameContentPanel);
            bindKey(InputType.DOWN, KEY_DOWN, eventsScheduler, gameContentPanel);
            bindKey(InputType.LEFT, KEY_LEFT, eventsScheduler, gameContentPanel);
            bindKey(InputType.RIGHT, KEY_RIGHT, eventsScheduler, gameContentPanel);
        });
        // score label
        final JLabel score = new JLabel();
        SwingUtils.changeFontSize(score, LABELS_SCALE);
        scoreUpdateLabel(score);
        setGameEventReaction = Optional.of(eventsSubscriber -> {
            eventsSubscriber.registerCallback(GameEvent.GAME_OVER, (gameEvent) -> {
                JOptionPane.showMessageDialog(gameContentPanel, gameEvent.name());
            });
            eventsSubscriber.registerCallback(GameEvent.DECREMENT_SCORE, (gameEvent) -> {
                scoreUpdateLabel(score);
            });
            eventsSubscriber.registerCallback(GameEvent.INCREMENT_SCORE, (gameEvent) -> {
                scoreUpdateLabel(score);
            });
            eventsSubscriber.registerCallback(GameEvent.VICTORY, (gameEvent) -> {
                JOptionPane.showMessageDialog(gameContentPanel, gameEvent.name());
            });
        });
        header.add(Box.createHorizontalStrut(SwingUtils.getNormalGap(header)));
        header.add(score);
        mainPanel.add(header, BorderLayout.NORTH);
        mainPanel.add(gameContentPanel, BorderLayout.CENTER);
        return mainPanel;
    }

    private void scoreUpdateLabel(final JLabel score) {
        SwingUtilities.invokeLater(() ->
        score.setText(SCORE + this.controller().getGameState().score().getValue())
        );
    }

    private <T> T getPostBuild(final Optional<T> value) {
        return Optionals.orIllegalState(value, "View has not been built yet.");
    } 

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber,
            final GameStateInfo gameState) {
        getPostBuild(setGameEventReaction).accept(eventsSubscriber);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(final EventsScheduler<InputEvent> eventsScheduler) {
        getPostBuild(setKeyBindings).accept(eventsScheduler);
    }

    private void bindKey(final InputType type, final int keyCode, final EventsScheduler<InputEvent> eventsScheduler,
            final JPanel out) {
        final InputEvent nonDropEvent = new InputEvent(type, false);
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,
                0, false), nonDropEvent);
        out.getActionMap().put(nonDropEvent, new AbstractAction() {

            @Override
            public void actionPerformed(final ActionEvent arg) {
                eventsScheduler.scheduleEvent(nonDropEvent);
            }

        });

        final InputEvent dropEvent = new InputEvent(type, true);
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,
                0, true), dropEvent);
        out.getActionMap().put(dropEvent, new AbstractAction() {

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
        gameMap.remove(gameObject);
        this.gamePanel.ifPresent(JPanel::repaint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject) {
        gameMap.put(gameObject, new GameObjectRepresentation(
            new GameObjectView(gameObject.type()).getImage().get(),
            gameObject.zIndex()
        ));
        this.gamePanel.ifPresent(JPanel::repaint);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        this.gamePanel.ifPresent(JPanel::repaint);
    }

}
