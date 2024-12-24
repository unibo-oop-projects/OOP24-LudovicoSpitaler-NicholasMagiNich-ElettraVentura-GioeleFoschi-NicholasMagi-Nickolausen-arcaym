package arcaym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class TestApp {
    @Test
    public void correctGreeting() {
        assertEquals("Hello, World", App.getGreeting("World"));
    }
}
