package arcaym.model.game.score.api;

import arcaym.model.game.core.events.api.EventsObserver;
import arcaym.model.game.events.api.GameEvent;

/**
 * Interface for a game score.
 */
public interface GameScore extends GameScoreInfo, EventsObserver<GameEvent> { }
