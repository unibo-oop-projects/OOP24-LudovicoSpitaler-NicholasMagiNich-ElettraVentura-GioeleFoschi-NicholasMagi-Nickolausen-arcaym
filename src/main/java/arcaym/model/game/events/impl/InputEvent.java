package arcaym.model.game.events.impl;

import arcaym.model.game.core.events.api.Event;
import arcaym.model.game.events.api.InputType;

/**
 * Record of Input Events.
 * 
 * @param inputType input type
 * @param drop wether the input is dropped or not
 */
public record InputEvent(InputType inputType, boolean drop) implements Event { }
