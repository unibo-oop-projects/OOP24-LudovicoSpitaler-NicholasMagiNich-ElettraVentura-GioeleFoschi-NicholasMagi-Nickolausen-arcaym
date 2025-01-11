package arcaym.model.game.score.impl;

import arcaym.common.utils.representation.FieldRepresentation;
import arcaym.common.utils.representation.StringRepresentation;
import arcaym.common.utils.representation.TypeRepresentation;
import arcaym.model.game.score.api.GameScore;

/**
 * Abstract implementation of {@link GameScore}.
 * It provides score access and initialization while leaving manipulation logic.
 */
@TypeRepresentation
public abstract class AbstractGameScore implements GameScore {

    private int value;

    /**
     * Check if amout is a positive value.
     * 
     * @param amount value to check
     * @return the value
     * @throws IllegalArgumentException if the value is negative
     */
    protected final int requirePositive(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Score manipulation amount must be positive");
        }
        return amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @FieldRepresentation
    public int getValue() {
        return this.value;
    }

    /**
     * Set current value.
     * 
     * @param value new value
     */
    protected void setValue(final int value) {
        this.value = value;
    }

    /**
     * Change current value of certain amount.
     * 
     * @param amount change value
     */
    protected void changeValue(final int amount) {
        this.value += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.ofObject(this);
    }

}
