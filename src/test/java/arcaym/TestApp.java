package arcaym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestApp {

    private App app;

    @BeforeEach
    void setup() {
        this.app = new App();
    }

    @Test
    void correctGreeting() {
        assertEquals("Hello, World", this.app.getGreeting("World"));
    }
}
