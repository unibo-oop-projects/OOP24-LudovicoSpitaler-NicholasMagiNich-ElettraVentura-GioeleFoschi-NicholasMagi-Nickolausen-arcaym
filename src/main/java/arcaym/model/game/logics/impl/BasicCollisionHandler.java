package arcaym.model.game.logics.impl;

import java.util.stream.Stream;

import arcaym.common.geometry.impl.Rectangles;
import arcaym.controller.game.scene.api.GameSceneInfo;
import arcaym.model.game.core.objects.api.GameObject;
import arcaym.model.game.core.objects.api.GameObjectInfo;
import arcaym.model.game.logics.api.CollisionHandler;

/**
 * Basic implementation of {@link CollisionHandler}.
 */
public class BasicCollisionHandler implements CollisionHandler {
    private final GameObject subject;

    /**
     * Constructor with gameObject (the object who "experiences" the collision) as a
     * argument.
     * 
     * @param subject
     */
    public BasicCollisionHandler(final GameObject subject) {
        this.subject = subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isColliding(final GameObjectInfo object) {
        return Rectangles.intersecting(object.boundaries(), subject.boundaries());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Stream<GameObjectInfo> getCollidingObjects(final GameSceneInfo scene) {
        return scene.getGameObjectsInfos().stream().filter(this::isColliding);
    }

}
