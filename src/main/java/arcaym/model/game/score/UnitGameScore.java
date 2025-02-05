package arcaym.model.game.score;

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
        if (unit < 0) {
            throw new IllegalArgumentException(
                new StringBuilder("Unit must be >= 0 (given: ")
                    .append(unit)
                    .append(')')
                    .toString()
            );
        }
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
