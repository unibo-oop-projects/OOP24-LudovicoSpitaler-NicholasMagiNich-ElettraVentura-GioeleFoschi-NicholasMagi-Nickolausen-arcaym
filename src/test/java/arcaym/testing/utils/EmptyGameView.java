package arcaym.testing.utils;

import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.game.api.GameView;

/**
 * Empty implementation of {@link GameView}.
 */
public class EmptyGameView implements GameView {

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(
        final EventsSubscriber<GameEvent> eventsSubscriber, 
        final GameStateInfo gameState
    ) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInputEventsScheduler(final EventsScheduler<InputEvent> eventsScheduler) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createObject(final GameObjectInfo gameObject) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateObject(final GameObjectInfo gameObject) { }

    /**
     * {@inheritDoc}
     */
    @Override
    public void destroyObject(final GameObjectInfo gameObjects) { }

}
