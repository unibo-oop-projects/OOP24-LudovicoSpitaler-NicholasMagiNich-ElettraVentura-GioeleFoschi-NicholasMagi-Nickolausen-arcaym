package arcaym.model.game.core.score.api;

import arcaym.model.game.core.score.impl.BaseGameScoreFactory;

/**
 * Interface for a {@link GameScore} factory.
 */
public interface GameScoreFactory {

    /**
     * Get new instance of the default factory implementation.
     * 
     * @return factory instance
     */
    static GameScoreFactory newDefault() {
        return new BaseGameScoreFactory();
    }

    /**
     * Create game score that varies by one each change.
     * 
     * @return resulting score
     */
    GameScore simpleScore();

    /**
     * Create game score that varies by a fixed unit each change.
     * 
     * @param unit unit amount
     * @return resulting score
     */
    GameScore ofUnit(int unit);

}
