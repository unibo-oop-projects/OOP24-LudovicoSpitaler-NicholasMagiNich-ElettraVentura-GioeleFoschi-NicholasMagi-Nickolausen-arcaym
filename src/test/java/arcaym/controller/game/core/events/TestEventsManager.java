package arcaym.controller.game.core.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.model.game.core.events.api.Event;
import arcaym.model.game.core.events.api.EventsManager;

class TestEventsManager {

    private Counter counter;

    static class Counter {

        private int value;
        private boolean isLocked;

        public void increment() {
            if (!isLocked) {
                this.value++;
            }
        }

        public void decrement() {
            if (!isLocked) {
                this.value--;
            }
        }

        public void lock() {
            this.isLocked = true;
        }

        public void reset() {
            this.isLocked = false;
            this.value = 0;
        }

    }

    enum NoPriorityEvent implements Event {
        INCREMENT,
        DECREMENT
    }

    enum PriorityEvent implements Event {
        LOCK(0),
        INCREMENT(1),
        DECREMENT(1);

        private final int priority;

        PriorityEvent(final int priority) {
            this.priority = priority;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public int priority() {
            return this.priority;
        }

    }

    @BeforeEach
    void setup() {
        this.counter = new Counter();
        this.counter.reset();
    }

    private void assertCounterValue(final int value) {
        assertEquals(this.counter.value, value);
    }

    private void assertCounterLocked(final boolean isLocked) {
        assertEquals(this.counter.isLocked, isLocked);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testSingleEvent(final EventsManager<NoPriorityEvent> eventsManager) {
        eventsManager.registerCallback(NoPriorityEvent.INCREMENT, e -> this.counter.increment());
        assertCounterValue(0);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        assertCounterValue(0);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        assertCounterValue(0);
        eventsManager.consumePendingEvents();
        assertCounterValue(1);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testEventValue(final EventsManager<NoPriorityEvent> eventsManager) {
        eventsManager.registerCallback(NoPriorityEvent.INCREMENT, e -> assertEquals(NoPriorityEvent.INCREMENT, e));
        eventsManager.registerCallback(NoPriorityEvent.DECREMENT, e -> assertEquals(NoPriorityEvent.DECREMENT, e));
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.consumePendingEvents();
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testMultipleEvents(final EventsManager<NoPriorityEvent> eventsManager) {
        eventsManager.registerCallback(NoPriorityEvent.INCREMENT, e -> this.counter.increment());
        eventsManager.registerCallback(NoPriorityEvent.DECREMENT, e -> this.counter.decrement());

        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(1);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testPriorityEvents(final EventsManager<PriorityEvent> eventsManager) {
        eventsManager.registerCallback(PriorityEvent.LOCK, e -> this.counter.lock());
        eventsManager.registerCallback(PriorityEvent.INCREMENT, e -> this.counter.increment());
        eventsManager.registerCallback(PriorityEvent.DECREMENT, e -> this.counter.decrement());

        eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(false);
        this.counter.reset();
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testMixedEvents(final EventsManager<Event> eventsManager) {
        eventsManager.registerCallback(PriorityEvent.LOCK, e -> this.counter.lock());
        eventsManager.registerCallback(NoPriorityEvent.INCREMENT, e -> this.counter.increment());
        eventsManager.registerCallback(NoPriorityEvent.DECREMENT, e -> this.counter.decrement());

        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(false);
        this.counter.reset();
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        eventsManager.scheduleEvent(NoPriorityEvent.DECREMENT);
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.scheduleEvent(PriorityEvent.LOCK);
        eventsManager.scheduleEvent(NoPriorityEvent.INCREMENT);
        eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
    }

}
