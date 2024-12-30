package arcaym.model.game.components;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Basic implementation of {@link GameComponent.Factory}.
 */
public class GameComponentFactory implements GameComponent.Factory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameComponent> ofType(final GameObjectType type) {
        return switch (Objects.requireNonNull(type)) {
            default -> Collections.emptyList();
        };
    }

}
