package arcaym.model.editor.impl;

import java.util.List;
import java.util.Map;

import arcaym.common.point.api.Point;
import arcaym.model.editor.api.Memento;
import arcaym.model.game.objects.api.GameObjectType;

public class ConcreteMemento implements Memento{

    private final List<Map<Point,GameObjectType>> state;
    private final GridImpl originator;

    public ConcreteMemento(GridImpl originator, List<Map<Point,GameObjectType>> state){
        this.originator = originator;
        this.state = state;
    }

    @Override
    public void restore() {
        this.originator.setState(state);
    }
    
}
