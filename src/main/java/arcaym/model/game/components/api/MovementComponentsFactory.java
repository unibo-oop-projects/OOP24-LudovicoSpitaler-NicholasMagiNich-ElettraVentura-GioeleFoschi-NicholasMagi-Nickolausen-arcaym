package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

public interface MovementComponentsFactory {

    public GameComponent fromInputMovement();

    public GameComponent automaticXMovement();

    public GameComponent automaticYMovement();

}
