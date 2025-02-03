package arcaym.controller.editor.api;

import java.util.Set;

import arcaym.controller.app.api.ControllerInfo;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for a {@link EditorController} restricted view.
 */
public interface EditorControllerInfo extends ControllerInfo { 

    /**
     * 
     * @return the set of objects owned by the user that can be used in the editor.
     */
    Set<GameObjectType> getOwnedObjects();
}
