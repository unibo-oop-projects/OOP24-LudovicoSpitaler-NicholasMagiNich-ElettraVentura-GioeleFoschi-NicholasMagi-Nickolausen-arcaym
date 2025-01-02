package arcaym.model.game.core.score.api;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;

/**
 * Interface for a game score manager.
 */
@TypeRepresentation
public interface GameScore {

    /**
     * Default amount used for value change operations.
     */
    int DEFAULT_AMOUNT = 1;

    /**
     * Get current score.
     * 
     * @return score value
     */
    @FieldRepresentation
    int getValue();

    /**
     * Increment score value.
     * 
     * @param amount how much to increment
     */
    void increment(int amount);

    /**
     * Increment score value of {@link GameScore#DEFAULT_AMOUNT}.
     */
    default void increment() {
        this.increment(DEFAULT_AMOUNT);
    }

    /**
     * Decrement score value.
     * 
     * @param amount how much to decrement
     */
    void decrement(int amount);

    /**
     * Decrement score value of {@link GameScore#DEFAULT_AMOUNT}.
     */
    default void decrement() {
        this.decrement(DEFAULT_AMOUNT);
    }

}
