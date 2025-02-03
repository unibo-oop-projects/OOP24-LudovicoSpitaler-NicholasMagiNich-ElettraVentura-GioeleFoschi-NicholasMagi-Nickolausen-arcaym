package arcaym.controller.editor.api;

import java.util.Set;

import arcaym.controller.app.api.Controller;
import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface for an editor controller.
 */
public interface EditorController extends Controller { 

    /**
     * 
     * @return the set of objects owned by the user that can be used in the editor.
     */
    Set<GameObjectType> getOwnedObjects();
}
