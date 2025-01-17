package arcaym.model.game.logics.impl;

import java.util.stream.Stream;

import arcaym.common.geometry.impl.Rectangles;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.logics.api.CollisionHandler;

/**
 * Basic implementation of {@link CollisionHandler}
 */
public class BasicCollisionHandler implements CollisionHandler {
    GameObject subject;

    public BasicCollisionHandler(final GameObject subject) {
        this.subject = subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isColliding(GameObjectInfo object) {
        return Rectangles.intersecting(object.boundaries(), subject.boundaries());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<GameObjectInfo> getCollidingObjects(GameSceneInfo scene) {
        return scene.getGameObjectsInfos().stream().filter(objects -> isColliding(objects));
    }

}
