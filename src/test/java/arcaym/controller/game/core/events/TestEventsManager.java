package arcaym.controller.game.core.events;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.controller.game.events.api.EventsManager;

class TestEventsManager {

    private int value;

    enum TestEvent {
        INCREMENT,
        DECREMENT
    }

    @BeforeEach
    void setup() {
        this.value = 0;
    }

    private void increment() {
        this.value++;
    }

    private void decrement() {
        this.value--;
    }

    private void assertValueEquals(final int value) {
        assertEquals(value, this.value);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testSingleEvent(final EventsManager<TestEvent> eventsManager) {
        eventsManager.registerCallback(TestEvent.INCREMENT, this::increment);
        assertValueEquals(0);
        eventsManager.consumePendingEvents();
        assertValueEquals(0);
        eventsManager.scheduleEvent(TestEvent.DECREMENT);
        assertValueEquals(0);
        eventsManager.consumePendingEvents();
        assertValueEquals(0);
        eventsManager.scheduleEvent(TestEvent.INCREMENT);
        assertValueEquals(0);
        eventsManager.consumePendingEvents();
        assertValueEquals(1);
    }

    @ParameterizedTest
    @ArgumentsSource(EventsManagersProvider.class)
    void testMultipleEvents(final EventsManager<TestEvent> eventsManager) {
        eventsManager.registerCallback(TestEvent.INCREMENT, this::increment);
        eventsManager.registerCallback(TestEvent.DECREMENT, this::decrement);
        eventsManager.consumePendingEvents();
        assertValueEquals(0);
        eventsManager.scheduleEvent(TestEvent.INCREMENT);
        eventsManager.scheduleEvent(TestEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertValueEquals(0);
        eventsManager.scheduleEvent(TestEvent.DECREMENT);
        eventsManager.scheduleEvent(TestEvent.INCREMENT);
        eventsManager.consumePendingEvents();
        assertValueEquals(0);
        eventsManager.scheduleEvent(TestEvent.INCREMENT);
        eventsManager.scheduleEvent(TestEvent.INCREMENT);
        eventsManager.scheduleEvent(TestEvent.DECREMENT);
        eventsManager.consumePendingEvents();
        assertValueEquals(1);
    }

}
