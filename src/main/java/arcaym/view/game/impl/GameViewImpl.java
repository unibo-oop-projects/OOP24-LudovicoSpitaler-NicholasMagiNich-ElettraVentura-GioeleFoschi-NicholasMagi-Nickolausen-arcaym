package arcaym.view.game.impl;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.game.api.GameView;

/**
 * Implementation of {@link GameView}.
 */
public class GameViewImpl implements GameView {

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(EventsSubscriber<GameEvent> eventsSubscriber, GameStateInfo gameState) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'registerEventsCallbacks'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(EventsScheduler<InputEvent> eventsScheduler) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setInputEventsScheduler'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(GameObjectInfo gameObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createObject'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(GameObjectInfo gameObject) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateObject'");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyObject(GameObjectInfo gameObjects) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'destroyObject'");
    }

}
