package arcaym;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TestApp {

    private AppLauncher app;

    @BeforeEach
    void setup() {
        this.app = new AppLauncher();
    }

    @Test
    void correctGreeting() {
        assertEquals("Hello, World", this.app.getGreeting("World"));
    }
}
