package arcaym.model.game.score.impl;

/**
 * Implementation of {@link GameScoreFactory} that uses a fixed-size base unit.
 */
public class UnitGameScore extends AbstractGameScore {

    private final int unit;

    /**
     * Initialize game score with unit.
     * 
     * @param unit base unit
     */
    public UnitGameScore(final int unit) {
        this.unit = requirePositive(unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void increment(final int amount) {
        this.changeValue(requirePositive(amount) * this.unit);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrement(final int amount) {
        this.changeValue(-requirePositive(amount) * this.unit);
    }

}
