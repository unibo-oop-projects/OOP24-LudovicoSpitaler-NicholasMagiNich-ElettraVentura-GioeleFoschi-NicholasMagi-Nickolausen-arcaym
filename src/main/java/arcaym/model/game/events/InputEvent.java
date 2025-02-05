package arcaym.model.game.events;

import arcaym.model.game.core.events.Event;

/**
 * Record of Input Events.
 * 
 * @param inputType input type
 * @param drop wether the input is dropped or not
 */
public record InputEvent(InputType inputType, boolean drop) implements Event { }
