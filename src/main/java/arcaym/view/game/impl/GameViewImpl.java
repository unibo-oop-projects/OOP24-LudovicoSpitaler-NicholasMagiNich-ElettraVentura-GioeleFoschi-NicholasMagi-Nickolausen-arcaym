package arcaym.view.game.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import arcaym.common.utils.Optionals;
import arcaym.model.game.core.engine.api.Game;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.components.CenteredPanel;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.game.api.GameView;
import arcaym.view.objects.GameObjectView;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link GameView}.
 */
public class GameViewImpl implements GameView, ViewComponent<JPanel> {
    private static final int SCALE = 2;
    private static final int KEY_UP = KeyEvent.VK_W;
    private static final int KEY_DOWN = KeyEvent.VK_S;
    private static final int KEY_LEFT = KeyEvent.VK_A;
    private static final int KEY_RIGHT = KeyEvent.VK_D;
    private final Game game; // Gamecontroller placeholder
    private Optional<Runnable> redrawPanelOperation = Optional.empty();
    private Optional<Runnable> scoreUpdaterOperation = Optional.empty();
    private Optional<Consumer<JPanel>> setKeyBindings = Optional.empty();
    private Optional<BiConsumer<JPanel, WindowInfo>> setGameEventReaction = Optional.empty();
    private final Map<GameObjectInfo, GameObjectView> gameMap = new HashMap<>();

    /**
     * Base constructor for GameViewImpl.
     * 
     * @param game
     */
    public GameViewImpl(final Game game) {
        this.game = game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        final JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setLayout(new BoxLayout(header, BoxLayout.LINE_AXIS));
        final JPanel gameContentPanel = new JPanel();
        gameContentPanel.setLayout(null);
        // Binding the operations of GameEventsReaction and KeyBindings to the panel of
        // the game view.
        getOperation(setGameEventReaction).accept(gameContentPanel, window);
        getOperation(setKeyBindings).accept(gameContentPanel);
        // The operation that redraws the game panel is saved and no longer null.
        // In this way, the panel is accessible to the other update methods.
        redrawPanelOperation = Optional.of(() -> {
            gameContentPanel.removeAll();
            this.gameMap.entrySet().stream().forEach(entry -> {
                final JLabel view = entry.getValue().build(window);
                gameContentPanel.add(view);
                setGameObjectPosition(entry.getKey(), view);
            });
        });
        final JLabel score = new JLabel();
        SwingUtils.changeFontSize(score, SCALE);
        // In a similar way to the redraw operation, the score updater operation has
        // access to the score label.
        scoreUpdaterOperation = Optional.of(() -> {
            scoreUpdateLabel(score, game.state().score().getValue());
        });
        getOperation(scoreUpdaterOperation).run();
        header.add(Box.createHorizontalStrut(SwingUtils.getNormalGap(header)));
        header.add(score);
        mainPanel.add(header, BorderLayout.NORTH);
        return mainPanel;
    }

    private void scoreUpdateLabel(final JLabel score, final Integer scoreValue) {
        score.setText("SCORE : " + scoreValue);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber,
            final GameStateInfo gameState) {
        // This method will be called before build().
        eventsSubscriber.registerCallback(GameEvent.GAME_OVER,
                (gameEvent) -> setGameEventReaction = Optional.of((out, window) -> {
                    final JLabel message = new JLabel(gameEvent.name());
                    out.add(new CenteredPanel().build(window, message));
                }));
        eventsSubscriber.registerCallback(GameEvent.INCREMENT_SCORE, (gameEvent) -> {
            getOperation(scoreUpdaterOperation).run();
        });
        eventsSubscriber.registerCallback(GameEvent.DECREMENT_SCORE, (gameEvent) -> {
            getOperation(scoreUpdaterOperation).run();
        });
        // The operation changes when the event is called, as its callback.
        eventsSubscriber.registerCallback(GameEvent.VICTORY,
                (gameEvent) -> setGameEventReaction = Optional.of((out, window) -> {
                    final JLabel message = new JLabel(gameEvent.name());
                    out.add(new CenteredPanel().build(window, message));
                }));
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
        this.gameMap.remove(gameObject);
        getOperation(redrawPanelOperation).run();
        getOperation(scoreUpdaterOperation).run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject) {
        this.gameMap.put(gameObject, new GameObjectView(gameObject.type()));
        getOperation(redrawPanelOperation).run();
        getOperation(scoreUpdaterOperation).run();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        getOperation(redrawPanelOperation).run();
        getOperation(scoreUpdaterOperation).run();
    }

    private <T> T getOperation(final Optional<T> operation) {
        return Optionals.orIllegalState(operation, "Illegal operation");
    }

    private void setGameObjectPosition(final GameObjectInfo object, final JLabel rendererdLabel) {
        rendererdLabel.setBounds((int) object.getPosition().x(), (int) object.getPosition().y(),
                (int) object.boundaries().base(), (int) object.boundaries().height());
    }

    /**
     * Debug only.
     * 
     * @param args
     */
    public static void main(final String[] args) {
        // SwingUtils.testComponent(new GameViewImpl()::build);
    }

}
