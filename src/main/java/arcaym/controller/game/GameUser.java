package arcaym.controller.game;

import arcaym.model.game.core.events.EventsObserver;
import arcaym.model.game.core.events.EventsScheduler;
import arcaym.model.game.core.objects.GameObjectInfo;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.events.InputEvent;

/**
 * Inteface for a game user.
 */
public interface GameUser extends EventsObserver<GameEvent> {

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
