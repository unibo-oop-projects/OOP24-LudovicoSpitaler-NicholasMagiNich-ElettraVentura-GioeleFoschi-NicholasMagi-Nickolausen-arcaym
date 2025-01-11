package arcaym.model.game.score.api;

import arcaym.controller.game.core.events.api.EventsObserver;
import arcaym.model.game.events.api.GameEvent;

/**
 * Interface for a game score manager.
 */
public interface GameScore extends GameScoreView, EventsObserver<GameEvent> { }
