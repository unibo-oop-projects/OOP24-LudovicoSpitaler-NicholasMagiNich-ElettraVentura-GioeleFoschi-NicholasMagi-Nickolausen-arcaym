package arcaym.view.game.api;

import java.util.Collection;

import arcaym.controller.game.core.events.api.EventsScheduler;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for a game view.
 */
public interface GameView {

    /**
     * Set input events scheduler to use.
     * 
     * @param eventsScheduler input events scheduler
     */
    void setInputEventsScheduler(EventsScheduler<InputEvent> eventsScheduler);

    /**
     * Create all the given objects.
     * 
     * @param gameObjects game objects
     */
    void createObjects(Collection<GameObjectView> gameObjects);

    /**
     * Render all the given objects.
     * 
     * @param gameObjects game objects
     */
    void updateObjects(Collection<GameObjectView> gameObjects);

    /**
     * Destroy all the given objects.
     * 
     * @param gameObjects game objects
     */
    void destroyObjects(Collection<GameObjectView> gameObjects);

    /**
     * React to game event.
     * 
     * @param event game event
     */
    void onGameEvent(GameEvent event);

}
