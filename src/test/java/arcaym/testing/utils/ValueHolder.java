package arcaym.testing.utils;

/**
 * Utility class to hold a mutable value with a final reference.
 */
public class ValueHolder<V> {

    private V value;

    /**
     * Initialize with starting value.
     * 
     * @param startingValue starting value
     */
    public ValueHolder(final V startingValue) {
        this.value = startingValue;
    }

    /**
     * Set value.
     * 
     * @param value new value
     */
    public void setValue(final V value) {
        this.value = value;
    }

    /**
     * Get value.
     * 
     * @return value
     */
    public V getValue() {
        return this.value;
    }

}
