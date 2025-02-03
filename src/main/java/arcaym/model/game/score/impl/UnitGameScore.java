package arcaym.model.game.score.impl;

import arcaym.model.game.score.api.GameScore;

/**
 * Implementation of {@link GameScore} that uses a fixed-size base unit.
 */
public class UnitGameScore extends AbstractGameScore {

    private final int unit;

    /**
     * Initialize with the given unit.
     * 
     * @param unit base unit
     */
    public UnitGameScore(final int unit) {
        this.unit = unit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void increment() {
        this.changeValue(this.unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void decrement() {
        this.changeValue(-this.unit);
    }

}
