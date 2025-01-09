package arcaym.model.game.core.scene;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.model.game.core.scene.api.GameSceneFactory;

class TestGameScene {

    @ParameterizedTest
    @ArgumentsSource(GameSceneFactoriesProvider.class)
    void testEmpty(final GameSceneFactory factory) {
        final var scene = factory.empty();
        assertEquals(scene.getObjects(), Collections.emptySet());
        scene.addObject(null);
    }

}
