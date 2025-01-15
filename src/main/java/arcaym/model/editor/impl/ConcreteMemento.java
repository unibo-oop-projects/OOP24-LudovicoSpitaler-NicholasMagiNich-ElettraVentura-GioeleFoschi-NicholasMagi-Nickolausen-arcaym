package arcaym.model.editor.impl;

import java.util.List;
import java.util.Map;

import arcaym.common.point.api.Point;
import arcaym.model.editor.api.Memento;
import arcaym.model.game.objects.api.GameObjectType;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

/**
 * A concrete memento coupled with the concrete grid class {@link GridImpl}.
 * Pattern implementation taken from:
 * https://refactoring.guru/design-patterns/memento
 */
public class ConcreteMemento implements Memento {

    private final List<Map<Point, GameObjectType>> state;
    private final GridImpl originator;

    /**
     * Creates a memento tightly coupled to the originator.
     * 
     * @param originator
     * @param state
     */
    @SuppressFBWarnings(
    value = { // List of bugs to be suppressed
        "EI_EXPOSE_REP2"
    },
    justification = "Specific Originator needed for the correct usage of the pattern"
    )
    public ConcreteMemento(final GridImpl originator, final List<Map<Point, GameObjectType>> state) {
        this.originator = originator;
        this.state = List.of(
            Map.copyOf(state.get(0)),
            Map.copyOf(state.get(1)),
            Map.copyOf(state.get(2))
            );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void restore() {
        this.originator.setState(state);
    }

}
