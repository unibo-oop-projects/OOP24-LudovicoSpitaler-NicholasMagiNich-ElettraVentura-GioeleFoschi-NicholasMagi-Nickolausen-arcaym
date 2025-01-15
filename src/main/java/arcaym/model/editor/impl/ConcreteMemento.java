package arcaym.model.editor.impl;

import arcaym.model.editor.api.Memento;

/**
 * A concrete memento coupled with the concrete grid class {@link GridImpl}.
 * Pattern implementation taken from:
 * https://refactoring.guru/design-patterns/memento
 */
public class ConcreteMemento implements Memento {

    // private final Optional<Map<Point, GameObjectType>> lowLayer;
    // private final Optional<Map<Point, GameObjectType>> entityLayer;
    // private final Optional<Map<Point, GameObjectType>> collectableLayer;

    // private final GridImpl originator;

    /**
     * {@inheritDoc}
     */
    @Override
    public void restore() {
        //this.originator.setState(lowLayer, entityLayer, collectableLayer);
    }

}
