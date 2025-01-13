package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;
import arcaym.model.game.core.objects.api.GameObject;

public interface GameComponentsFactory {

    public GameComponent obstacleCollision();

    public GameComponent wallCollision();

    public GameComponent coinCollision();

    public GameComponent fromKeyBoardMovement();

    public GameComponent automaticMovement();

}
