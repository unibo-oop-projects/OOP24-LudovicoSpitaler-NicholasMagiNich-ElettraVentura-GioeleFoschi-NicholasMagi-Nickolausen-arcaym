package arcaym.model.game.core.score;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;

import arcaym.model.game.core.score.api.GameScore;
import arcaym.model.game.core.score.api.GameScoreFactory;

class TestGameScore {

    @ParameterizedTest
    @ArgumentsSource(GameScoreFactoriesProvider.class)
    void testSimpleScore(final GameScoreFactory factory) {
        final var score = factory.simpleScore();
        final var startingValue = score.getValue();
        score.increment(2);
        assertEquals(startingValue + 2, score.getValue());
        score.increment();
        assertEquals(startingValue + 2 + GameScore.DEFAULT_AMOUNT, score.getValue());
        score.decrement(2);
        assertEquals(startingValue + GameScore.DEFAULT_AMOUNT, score.getValue());
        score.decrement();
        assertEquals(startingValue, score.getValue());
        score.decrement(startingValue);
        assertEquals(0, score.getValue());
    }

    @ParameterizedTest
    @ArgumentsSource(GameScoreFactoriesProvider.class)
    void testStepScore(final GameScoreFactory factory) {
        final var unit = 3;
        final var score = factory.ofUnit(unit);
        final var startingValue = score.getValue();
        score.increment(2);
        assertEquals(startingValue + (2 * unit), score.getValue());
        score.increment();
        assertEquals(startingValue + ((2 + GameScore.DEFAULT_AMOUNT) * unit), score.getValue());
        score.decrement(2);
        assertEquals(startingValue + (GameScore.DEFAULT_AMOUNT * unit), score.getValue());
        score.decrement();
        assertEquals(startingValue, score.getValue());
        score.decrement(startingValue);
        assertEquals(0, score.getValue());
    }

}
