package arcaym.controller.game.core.api;

import java.util.Collection;

import arcaym.controller.game.events.api.EventsObserver;
import arcaym.controller.game.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectView;
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
     * Create all the given objects.
     * 
     * @param gameObject game objects
     */
    void createObject(GameObjectView gameObject);

    /**
     * Render all the given objects.
     * 
     * @param gameObjects game objects
     */
    void updateObjects(Collection<GameObjectView> gameObjects);

    /**
     * Destroy all the given objects.
     * 
     * @param gameObject game objects
     */
    void destroyObject(GameObjectView gameObject);

}
