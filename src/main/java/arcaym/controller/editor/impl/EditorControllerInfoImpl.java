package arcaym.controller.editor.impl;

import java.util.Set;

import arcaym.controller.editor.api.EditorControllerInfo;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.impl.UserStateImpl;

public class EditorControllerInfoImpl implements EditorControllerInfo {

    // TODO(Change into something read-only [not the record, because it does not stay updated!])
    private final UserState userState;
    
    public EditorControllerInfoImpl() {
        this.userState = new UserStateImpl();
    }

    @Override
    public void close() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'close'");
    }

    @Override
    public Set<GameObjectType> getOwnedObjects() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getOwnedObjects'");
    }
    
}
