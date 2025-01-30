package arcaym.view.game.impl;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import arcaym.controller.app.api.GameController;
import arcaym.controller.game.core.api.Game;
import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.core.impl.GameControllerImpl;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputType;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.core.api.ViewComponent;
import arcaym.view.core.api.WindowInfo;
import arcaym.view.game.api.GameView;
import arcaym.view.utils.SwingUtils;

/**
 * Implementation of {@link GameView}.
 */
public class GameViewImpl implements GameView, ViewComponent<JPanel> {
    private static final int KEY_UP = KeyEvent.VK_W;
    private static final int KEY_DOWN = KeyEvent.VK_S;
    private static final int KEY_LEFT = KeyEvent.VK_A;
    private static final int KEY_RIGHT = KeyEvent.VK_D;
    private final GameController controller = new GameControllerImpl();
    private Map<InputEvent, String> keyMap = new HashMap<>();
    private final Game game = null;
    private JPanel out = new JPanel();

    // public GameViewImpl(final Game game) {
    public GameViewImpl() {
        keyMap.put(new InputEvent(InputType.UP, false), "UP");
        keyMap.put(new InputEvent(InputType.UP, true), "released UP");

        keyMap.put(new InputEvent(InputType.DOWN, false), "DOWN");
        keyMap.put(new InputEvent(InputType.DOWN, true), "released DOWN");

        keyMap.put(new InputEvent(InputType.LEFT, false), "LEFT");
        keyMap.put(new InputEvent(InputType.LEFT, true), "released LEFT");

        keyMap.put(new InputEvent(InputType.RIGHT, false), "RIGHT");
        keyMap.put(new InputEvent(InputType.RIGHT, true), "released RIGHT");
        // this.game = game;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public JPanel build(final WindowInfo window) {
        final JPanel mainContentPanel = new JPanel();
        this.out = mainContentPanel;
        setInputEventsScheduler(event -> System.out.println("Current" + event));
        return mainContentPanel;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyObject(final GameObjectInfo gameObjects) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destroyObject'");
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
        bindKey(InputType.UP, KEY_UP, eventsScheduler);
        bindKey(InputType.DOWN, KEY_DOWN, eventsScheduler);
        bindKey(InputType.LEFT, KEY_LEFT, eventsScheduler);
        bindKey(InputType.RIGHT, KEY_RIGHT, eventsScheduler);
    }

    private void bindKey(final InputType type, final int keyCode, final EventsScheduler<InputEvent> eventsScheduler) {
        InputEvent nonDropEvent = new InputEvent(type, false);
        String nonDropKey = type.name() + "_PRESSED";
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, false), nonDropKey);
        out.getActionMap().put(nonDropKey, new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent arg) {
                eventsScheduler.scheduleEvent(nonDropEvent);
            }

        });

        InputEvent dropEvent = new InputEvent(type, true);
        String dropKey = type.name() + "_RELEASED";
        out.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(keyCode, 0, true), dropKey);
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
    public void createObject(final GameObjectInfo gameObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createObject'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateObject'");
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