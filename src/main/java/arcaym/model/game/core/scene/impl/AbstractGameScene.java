package arcaym.model.game.core.scene.impl;

import arcaym.common.utils.representation.StringRepresentation;
import arcaym.model.game.core.scene.api.GameScene;

/**
 * Abstract implementation of {@link GameScene}.
 */
public abstract class AbstractGameScene implements GameScene {

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return StringRepresentation.toString(this);
    }

}
