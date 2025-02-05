package arcaym.model.game.core.events;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread safe implementation of {@link EventsManager}.
 * 
 * @param <T> events type
 */
public class ThreadSafeEventsManager<T extends Event> implements EventsManager<T> {

    private static final long POLL_TIMEOUT = 1;
    private static final TimeUnit POLL_TIMEOUT_UNIT = TimeUnit.MILLISECONDS;
    private static final int EVENTS_QUEUE_INITIAL_CAPACITY = 10;
    private static final Logger LOGGER = LoggerFactory.getLogger(ThreadSafeEventsManager.class);

    private final ConcurrentMap<T, List<Consumer<T>>> eventsCallbacks = new ConcurrentHashMap<>();
    private final BlockingQueue<T> pendingEvents = new PriorityBlockingQueue<>(
        EVENTS_QUEUE_INITIAL_CAPACITY, 
        Event::compare
    );

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCallback(final T event, final Consumer<T> callback) {
        if (!this.eventsCallbacks.containsKey(Objects.requireNonNull(event))) {
            this.eventsCallbacks.put(event, new LinkedList<>());
        }
        this.eventsCallbacks.get(event).add(Objects.requireNonNull(callback));
        LOGGER.info(
            new StringBuilder("Registered new callback to ")
                .append(event)
                .toString()
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final T event) {
        this.pendingEvents.add(Objects.requireNonNull(event));
        LOGGER.info(new StringBuilder("Scheduled event ").append(event).toString());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void consumePendingEvents() {
        try {
            while (!this.pendingEvents.isEmpty()) {
                final var event = this.pendingEvents.poll(POLL_TIMEOUT, POLL_TIMEOUT_UNIT);
                LOGGER.info(new StringBuilder("Raising event ").append(event).toString());
                this.eventsCallbacks.getOrDefault(event, Collections.emptyList()).forEach(c -> c.accept(event));
                if (event.isTerminal()) {
                    this.pendingEvents.clear();
                }
            }
            LOGGER.info("Finished consuming all pending events");
        } catch (InterruptedException e) {
            LOGGER.warn("Pending events poll interrupted");
        }
    }

}
