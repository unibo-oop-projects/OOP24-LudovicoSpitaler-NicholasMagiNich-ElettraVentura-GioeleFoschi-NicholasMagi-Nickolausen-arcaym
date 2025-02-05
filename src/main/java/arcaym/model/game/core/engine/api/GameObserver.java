package arcaym.model.game.core.engine.api;

import arcaym.model.game.core.events.api.EventsObserver;
import arcaym.model.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.impl.InputEvent;

public interface GameObserver extends EventsObserver<GameEvent> {

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
     * @param zIndex z index
     */
    void createObject(GameObjectInfo gameObject, int zIndex);

    /**
     * Update an existing game object.
     * 
     * @param gameObject game object
     */
    void updateObject(GameObjectInfo gameObject);

    /**
     * Destroy a game object.
     * 
     * @param gameObject game object
     */
    void destroyObject(GameObjectInfo gameObject);

}
