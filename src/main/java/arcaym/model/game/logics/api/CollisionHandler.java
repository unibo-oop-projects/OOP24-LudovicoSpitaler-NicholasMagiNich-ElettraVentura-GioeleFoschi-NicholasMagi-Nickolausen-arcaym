package arcaym.model.game.logics.api;

import java.util.stream.Stream;

import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.objects.api.GameObjectInfo;

public interface CollisionHandler {
    boolean isColliding(GameObjectInfo objectInfo);
    Stream<GameObjectInfo> getCollidingObjects(GameSceneInfo scene);
}
