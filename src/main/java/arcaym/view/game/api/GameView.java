package arcaym.view.game.api;

import arcaym.model.game.core.events.api.EventsObserver;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;
import arcaym.view.app.api.View;

/**
 * Interface for a game view.
 */
public interface GameView extends View, EventsObserver<GameEvent> {

    /**
     * Set input events scheduler to use.
     * 
     * @param eventsScheduler input events scheduler
     */
    void setInputEventsScheduler(EventsScheduler<InputEvent> eventsScheduler);

    /**
     * Create a game object.
     * 
     * @param gameObject game object
     */
    void createObject(GameObjectInfo gameObject);

    /**
     * Update an existing game object.
     * 
     * @param gameObject game object
     */
    void updateObject(GameObjectInfo gameObject);

    /**
     * Destroy a game object.
     * 
     * @param gameObjects game object
     */
    void destroyObject(GameObjectInfo gameObjects);

}
