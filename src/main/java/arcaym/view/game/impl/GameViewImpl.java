package arcaym.view.game.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;
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
    private Map<InputEvent, String> keyMap = new HashMap<>();
    private final Game game = null; //
    private Optional<Runnable> redrawPanelOperation = Optional.empty();
    private Optional<Runnable> scoreUpdaterOperation = Optional.empty();
    private Consumer<JPanel> setKeyBindings;
    private Map<GameObjectInfo, GameObjectView> gameMap = new HashMap<>();

    // public GameViewImpl(final Game game) {
    public GameViewImpl() {
        // this.game = game;
        keyMap.put(new InputEvent(InputType.UP, false), "UP");
        keyMap.put(new InputEvent(InputType.UP, true), "released UP");

        keyMap.put(new InputEvent(InputType.DOWN, false), "DOWN");
        keyMap.put(new InputEvent(InputType.DOWN, true), "released DOWN");

        keyMap.put(new InputEvent(InputType.LEFT, false), "LEFT");
        keyMap.put(new InputEvent(InputType.LEFT, true), "released LEFT");

        keyMap.put(new InputEvent(InputType.RIGHT, false), "RIGHT");
        keyMap.put(new InputEvent(InputType.RIGHT, true), "released RIGHT");

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
        setKeyBindings.accept(gameContentPanel);
        gameContentPanel.setLayout(null);
        redrawPanelOperation = Optional.of(() -> {
            gameContentPanel.removeAll();
            this.gameMap.entrySet().stream().forEach(entry -> {
                JLabel view = entry.getValue().build(window);
                gameContentPanel.add(view);
                setGameObjectPosition(entry.getKey(), view);
            });
        });
        final JLabel score = new JLabel();
        SwingUtils.changeFontSize(score, SCALE);
        scoreUpdateLabel(score, game.state().score().getValue());
        scoreUpdaterOperation = Optional.of(() -> {
            scoreUpdateLabel(score, game.state().score().getValue());
        });
        header.add(Box.createHorizontalStrut(SwingUtils.getNormalGap(header)));
        header.add(score);
        mainPanel.add(header, BorderLayout.NORTH);
        return mainPanel;
    }

    private void scoreUpdateLabel(JLabel score, Integer scoreValue) {
        score.setText("SCORE : " + String.valueOf(scoreValue));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber,
            final GameStateInfo gameState) {
        eventsSubscriber.registerCallback(GameEvent.GAME_OVER, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(final EventsScheduler<InputEvent> eventsScheduler) {
        //inizializzazione di un consumer JPanel che usa eventscheduler. verrà usato in build. veramente brutto.
        setKeyBindings = (out)->{
            bindKey(InputType.UP, KEY_UP, eventsScheduler,out);
            bindKey(InputType.DOWN, KEY_DOWN, eventsScheduler,out);
            bindKey(InputType.LEFT, KEY_LEFT, eventsScheduler,out);
            bindKey(InputType.RIGHT, KEY_RIGHT, eventsScheduler,out);
        };
    }

    private void bindKey(final InputType type, final int keyCode, final EventsScheduler<InputEvent> eventsScheduler, final JPanel out) {
        InputEvent nonDropEvent = new InputEvent(type, false);
        String nonDropKey = type.name() + "_PRESSED";
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode,
        0, false), nonDropKey);
        out.getActionMap().put(nonDropKey, new AbstractAction() {

        @Override
        public void actionPerformed(ActionEvent arg) {
        eventsScheduler.scheduleEvent(nonDropEvent);
        }

        });

        InputEvent dropEvent = new InputEvent(type, true);
        String dropKey = type.name() + "_RELEASED";
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

        runOperation(redrawPanelOperation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject) {
        this.gameMap.put(gameObject, new GameObjectView(gameObject.type()));
        runOperation(redrawPanelOperation);
        runOperation(scoreUpdaterOperation);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        this.gameMap.replace(gameObject, new GameObjectView(gameObject.type()));
        runOperation(redrawPanelOperation);
    }

    private void runOperation(final Optional<Runnable> operation) {
        if (operation.isPresent()) {
            operation.get().run();
        } else {
            throw new IllegalStateException("Game View is not available/built yet.");
        }
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
        SwingUtils.testComponent(new GameViewImpl()::build);
    }

}