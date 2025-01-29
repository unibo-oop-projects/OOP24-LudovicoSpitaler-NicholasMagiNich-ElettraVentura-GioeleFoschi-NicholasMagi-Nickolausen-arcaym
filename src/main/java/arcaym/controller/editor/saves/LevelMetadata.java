package arcaym.controller.editor.saves;

import arcaym.common.utils.Position;
import arcaym.model.editor.EditorType;

/**
 * A class defining a level metadata.
 * @param levelName The level name
 * @param type The type of the level (Normal, Sandbox, etc...)
 * @param size The size of the grid
 * @param uuid The id of the file save 
 */
public record LevelMetadata(String levelName, String uuid, EditorType type, Position size) {
}
