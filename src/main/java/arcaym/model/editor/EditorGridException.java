package arcaym.model.editor;

/**
 * A checked exeption for the Grid Object.
 */
public class EditorGridException extends Exception {

    @java.io.Serial
    static final long serialVersionUID = -3387516993124229948L;

    private final String message;
    private final String actionPerformed;
    /**
     * The constructor for the grid error message.
     * @param message the message explaining the cause of the error
     * @param actionPerformed weather the object that caused the error were being placed or removed
     * @param stackTrace the error that caused this error
     */
    public EditorGridException(final String message, final String actionPerformed, final Exception stackTrace) {
        super(stackTrace);
        this.actionPerformed = actionPerformed;
        this.message = message;
    }

    /**
     * The constructor for the grid error message.
     * 
     * @param message the message explaining the cause of the error
     * @param actionPerfomed What kind of action triggered the error
     */
    public EditorGridException(final String message, final String actionPerfomed) {
        super();
        this.actionPerformed = actionPerfomed;
        this.message = message;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Cannot " + actionPerformed + message;
    }
}
