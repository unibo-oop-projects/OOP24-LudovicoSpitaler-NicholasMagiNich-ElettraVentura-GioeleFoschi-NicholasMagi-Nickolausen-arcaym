package arcaym.model.editor;

/**
 * A checked exeption for the Grid Object.
 */
public class EditorGridException extends Exception {

    @java.io.Serial
    static final long serialVersionUID = -3387516993124229948L;

    private final String message;
    private final boolean objectPlaced;
    /**
     * The constructor for the grid error message.
     * @param message the message explaining the cause of the error
     * @param objectPlaced weather the object that caused the error were being placed or removed
     * @param stackTrace the error that caused this error
     */
    public EditorGridException(final String message, final boolean objectPlaced, final Exception stackTrace) {
        super(stackTrace);
        this.objectPlaced = objectPlaced;
        this.message = message;
    }

    /**
     * The constructor for the grid error message.
     * 
     * @param message      the message explaining the cause of the error
     * @param objectPlaced weather the object that caused the error were being placed or removed
     */
    public EditorGridException(final String message, final boolean objectPlaced) {
        super();
        this.objectPlaced = objectPlaced;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Cannot " + (objectPlaced ? "place" : "remove") + " objects: " + message + ".\n" + super.toString();
    }
}
