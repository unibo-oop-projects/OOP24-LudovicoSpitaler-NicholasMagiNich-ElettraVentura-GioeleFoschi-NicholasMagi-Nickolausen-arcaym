package arcaym.model.game.objects;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import arcaym.model.game.core.components.api.GameObjectComponent;

/**
 * Basic implementation of {@link GameObjectComponent.Factory}.
 */
public class GameObjectComponentFactory implements GameObjectComponent.Factory {

    /**
     * {@inheritDoc}
     */
    @Override
    public Collection<GameObjectComponent> ofType(final GameObjectType type) {
        return switch (Objects.requireNonNull(type)) {
            default -> Collections.emptyList();
        };
    }

}
