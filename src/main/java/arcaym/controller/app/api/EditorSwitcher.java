package arcaym.controller.app.api;

import arcaym.controller.editor.api.Editor;

/**
 * An interface exposing the method used to switch from the playable level to the editor.
 */
public interface EditorSwitcher {

    /**
     * Switches context from the level to the editor.
     * @return the controller used by the editor.
     */
    Editor returnToEditor();
}
