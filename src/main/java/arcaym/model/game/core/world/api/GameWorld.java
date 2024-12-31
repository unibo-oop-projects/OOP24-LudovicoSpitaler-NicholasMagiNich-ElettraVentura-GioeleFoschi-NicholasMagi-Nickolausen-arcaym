package arcaym.model.game.core.world.api;

import arcaym.common.utils.representation.Representation.FieldRepresentation;
import arcaym.common.utils.representation.Representation.TypeRepresentation;
import arcaym.model.game.core.scene.api.GameScene;
import arcaym.model.game.core.score.api.GameScore;

/**
 * Interface for a game world.
 */
@TypeRepresentation
public interface GameWorld {

    /**
     * Get game scene.
     * 
     * @return game scene
     */
    @FieldRepresentation
    GameScene scene();

    /**
     * Get game score.
     * 
     * @return game score
     */
    @FieldRepresentation
    GameScore score();

}
