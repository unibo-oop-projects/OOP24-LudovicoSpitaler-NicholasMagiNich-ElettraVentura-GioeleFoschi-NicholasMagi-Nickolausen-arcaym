package arcaym.controller.game.core.events.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.logging.Logger;

import arcaym.controller.game.core.events.api.EventsManager;

/**
 * Thread safe implementation of {@link EventsManager}.
 * 
 * @param <T> events type
 */
public class ThreadSafeEventsManager<T> implements EventsManager<T> {

    private static final int POLL_TIMEOUT = 1;
    private static final TimeUnit POLL_TIME_UNIT = TimeUnit.MILLISECONDS;
    private static final Logger LOGGER = Logger.getLogger(ThreadSafeEventsManager.class.getName());

    private final ConcurrentMap<T, List<Consumer<T>>> callbacks = new ConcurrentHashMap<>();
    private final BlockingQueue<T> pendingEvents = new LinkedBlockingQueue<>();

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerCallback(final T event, final Consumer<T> callback) {
        if (!this.callbacks.containsKey(Objects.requireNonNull(event))) {
            this.callbacks.put(event, new LinkedList<>());
        }
        this.callbacks.get(event).add(Objects.requireNonNull(callback));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void scheduleEvent(final T event) {
        this.pendingEvents.add(Objects.requireNonNull(event));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void notifyObservers() {
        T event;
        try {
            while ((event = this.pendingEvents.poll(POLL_TIMEOUT, POLL_TIME_UNIT)) != null) {
                for (final var callback : this.callbacks.getOrDefault(event, Collections.emptyList())) {
                    callback.accept(event);
                };
            }
        } catch (InterruptedException e) {
            LOGGER.warning("Pending events poll interrupted");
        }
    }

}
