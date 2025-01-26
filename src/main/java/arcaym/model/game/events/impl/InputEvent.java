package arcaym.model.game.events.impl;

import arcaym.model.game.events.api.Event;
import arcaym.model.game.events.api.InputType;

public record InputEvent(InputType inputType, boolean drop) implements Event {

}
