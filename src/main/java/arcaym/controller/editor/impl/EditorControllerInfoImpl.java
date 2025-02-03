package arcaym.controller.editor.impl;

import java.util.Collections;
import java.util.Set;

import arcaym.controller.editor.api.EditorControllerInfo;
import arcaym.controller.user.api.UserStateSerializerInfo;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserStateInfo;

/**
 * Implementation of {@link EditorControllerInfo}.
 */
public class EditorControllerInfoImpl implements EditorControllerInfo {

    private final UserStateInfo userState;

    /**
     * Default constructor.
     */
    public EditorControllerInfoImpl() { 
        final UserStateSerializerInfo serializer = new UserStateSerializerImpl();
        this.userState = serializer.getUpdatedState();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void close() { }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getOwnedObjects() {
        return Collections.unmodifiableSet(userState.itemsOwned());
    }

}
