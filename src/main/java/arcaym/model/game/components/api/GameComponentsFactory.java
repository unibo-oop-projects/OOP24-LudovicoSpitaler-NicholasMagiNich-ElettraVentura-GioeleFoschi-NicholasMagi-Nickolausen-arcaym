package arcaym.model.game.components.api;

import arcaym.model.game.core.components.api.GameComponent;

public interface GameComponentsFactory {

    public GameComponent obstacleCollision();

    public GameComponent wallCollision();

    public GameComponent coinCollision();

    public GameComponent fromKeyBoardMovement();

    public GameComponent automaticXMovement();

    public GameComponent automaticYMovement();

    public GameComponent reachedGoal();

}
