package arcaym.view.game.api;

import java.util.Collection;

import arcaym.controller.game.core.events.api.EventsScheduler;
import arcaym.controller.game.core.events.api.GameEventsObserver;
import arcaym.model.game.core.objects.api.GameObjectView;
import arcaym.model.game.events.api.InputEvent;

/**
 * Interface for a game view.
 */
public interface GameView extends GameEventsObserver {

    /**
     * Set input events scheduler to use.
     * 
     * @param eventsScheduler input events scheduler
     */
    void setInputEventsScheduler(EventsScheduler<InputEvent> eventsScheduler);

    /**
     * Render all the given objects.
     * 
     * @param gameObjects game objects
     */
    void render(Collection<GameObjectView> gameObjects);

}
