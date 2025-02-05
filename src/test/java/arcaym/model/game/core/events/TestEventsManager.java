package arcaym.model.game.core.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import arcaym.model.game.core.events.api.Event;
import arcaym.model.game.core.events.api.EventsManager;
import arcaym.model.game.core.events.impl.ThreadSafeEventsManager;

class TestEventsManager {

    private Counter counter;
    private EventsManager<Event> eventsManager;

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

    enum NormalEvent implements Event {
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

    enum TerminalEvent implements Event {
        TERMINAL_EVENT;

        /**
         * {@inheritDoc}
         */
        @Override
        public boolean isTerminal() {
            return true;
        }
    }

    @BeforeEach
    void setup() {
        this.eventsManager = new ThreadSafeEventsManager<>();
        this.counter = new Counter();
        this.counter.reset();
    }

    private void assertCounterValue(final int value) {
        assertEquals(this.counter.value, value);
    }

    private void assertCounterLocked(final boolean isLocked) {
        assertEquals(this.counter.isLocked, isLocked);
    }

    @Test
    void testSingleEvent() {
        this.eventsManager.registerCallback(NormalEvent.INCREMENT, e -> this.counter.increment());
        assertCounterValue(0); // starting point
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0); // no events were scheduled
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        assertCounterValue(0); // decrement is scheduled but not yet consumed
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0); // decrement has no callback registered
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        assertCounterValue(0); // increment is scheduled but not consumed
        this.eventsManager.consumePendingEvents();
        assertCounterValue(1); // increment performed
    }

    @Test
    void testEventValue() {
        // assert inside callback that the correct event is received
        this.eventsManager.registerCallback(
            NormalEvent.INCREMENT, 
            e -> assertEquals(NormalEvent.INCREMENT, e)
        );
        this.eventsManager.registerCallback(
            NormalEvent.DECREMENT, 
            e -> assertEquals(NormalEvent.DECREMENT, e)
        );
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.consumePendingEvents();
    }

    @Test
    void testMultipleEvents() {
        this.eventsManager.registerCallback(NormalEvent.INCREMENT, e -> this.counter.increment());
        this.eventsManager.registerCallback(NormalEvent.DECREMENT, e -> this.counter.decrement());

        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(1);
    }

    @Test
    void testPriorityEvents() {
        this.eventsManager.registerCallback(PriorityEvent.LOCK, e -> this.counter.lock());
        this.eventsManager.registerCallback(PriorityEvent.INCREMENT, e -> this.counter.increment());
        this.eventsManager.registerCallback(PriorityEvent.DECREMENT, e -> this.counter.decrement());

        this.eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        this.eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        this.eventsManager.consumePendingEvents(); // same priority
        assertCounterValue(0);
        assertCounterLocked(false);
        this.counter.reset();
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        this.eventsManager.consumePendingEvents(); // lock has priority so decrement has no effect
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        this.eventsManager.scheduleEvent(PriorityEvent.DECREMENT);
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.consumePendingEvents(); // same result as before regardless of the events order
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        this.eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.scheduleEvent(PriorityEvent.INCREMENT);
        this.eventsManager.consumePendingEvents(); // lock has priority on both increment and decrement
        assertCounterValue(0);
        assertCounterLocked(true);
    }

    @Test
    void testMixedPriority() {
        // assert priority works event againts no-priority events
        this.eventsManager.registerCallback(PriorityEvent.LOCK, e -> this.counter.lock());
        this.eventsManager.registerCallback(NormalEvent.INCREMENT, e -> this.counter.increment());
        this.eventsManager.registerCallback(NormalEvent.DECREMENT, e -> this.counter.decrement());

        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(false);
        this.counter.reset();
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        this.eventsManager.scheduleEvent(NormalEvent.DECREMENT);
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
        this.counter.reset();
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.scheduleEvent(PriorityEvent.LOCK);
        this.eventsManager.scheduleEvent(NormalEvent.INCREMENT);
        this.eventsManager.consumePendingEvents();
        assertCounterValue(0);
        assertCounterLocked(true);
    }

    @Test
    void testTerminalEvents() {
        final var raisedEvents = new ArrayList<Event>();
        this.eventsManager.registerCallback(TerminalEvent.TERMINAL_EVENT, raisedEvents::add);
        final var numberOfSchedules = 10; // schedule the event n times
        for (int i = 0; i < numberOfSchedules; i++) {
            this.eventsManager.scheduleEvent(TerminalEvent.TERMINAL_EVENT);
        }
        this.eventsManager.consumePendingEvents();
        // because it's terminal, the queue is cleared after the first raise
        assertEquals(1, raisedEvents.size());
    }

}
