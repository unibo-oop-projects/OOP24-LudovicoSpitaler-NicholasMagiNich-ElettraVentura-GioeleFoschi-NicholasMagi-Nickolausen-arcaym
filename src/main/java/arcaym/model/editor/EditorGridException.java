package arcaym.model.editor;

/**
 * A checked exeption for the Grid Object.
 */
public class EditorGridException extends Exception {

    @java.io.Serial
    static final long serialVersionUID = -3387516993124229948L;

    private final String message;

    /**
     * The constructor for the grid error message.
     * @param message the message explaining the cause of the error
     */
    public EditorGridException(final String message) {
        super(message);
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Cannot place objects: " + message;
    }
}
