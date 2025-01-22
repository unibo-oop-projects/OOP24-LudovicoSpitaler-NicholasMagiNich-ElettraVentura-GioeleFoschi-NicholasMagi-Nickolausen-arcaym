package arcaym.model.editor;

/**
 * Exception thrown when a map constriant is failed.
 */
public class ConstraintFailedException extends Exception {

    @java.io.Serial
    static final long serialVersionUID = -3387516993124229948L;
    private static final String EXCEPTION_INIT_MESSAGE = "The placement of the objects did not follow the constraint: ";

    private final String message;

    /**
     * Constructor of Exception.
     * @param message the message to write
     */
    public ConstraintFailedException(final String message) {
        super();
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return EXCEPTION_INIT_MESSAGE + message;
    }
}
