package arcaym.controller.game.core.api;

import java.util.Collection;

import arcaym.controller.game.events.api.EventsObserver;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for a game observer.
 */
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
     */
    void createObject(GameObjectInfo gameObject);

    /**
     * Render all the given objects.
     * 
     * @param gameObjects game objects
     */
    void updateObjects(Collection<GameObjectInfo> gameObjects);

    /**
     * Destroy a game object.
     * 
     * @param gameObjects game object
     */
    void destroyObject(GameObjectInfo gameObjects);

}
