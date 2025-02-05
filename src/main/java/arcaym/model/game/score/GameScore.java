package arcaym.model.game.score;

import arcaym.model.game.core.events.EventsObserver;
import arcaym.model.game.events.GameEvent;

/**
 * Interface for a game score.
 */
public interface GameScore extends GameScoreInfo, EventsObserver<GameEvent> { }
