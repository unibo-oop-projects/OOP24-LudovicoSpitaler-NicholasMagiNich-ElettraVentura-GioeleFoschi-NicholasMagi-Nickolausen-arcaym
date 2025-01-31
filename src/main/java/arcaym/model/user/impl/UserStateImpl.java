package arcaym.model.user.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsObserver;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;

/**
 * Implementation of {@link UserState}.
 */
public class UserStateImpl implements UserState, EventsObserver<GameEvent> {

    private int currentCredit;
    private final Set<GameObjectType> itemsOwned = EnumSet.noneOf(GameObjectType.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        return this.currentCredit;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getPurchasedGameObjects() {
        return Collections.unmodifiableSet(itemsOwned);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewGameObject(final GameObjectType gameObject) {
        this.itemsOwned.add(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        validateAmount(amount);
        this.currentCredit += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        validateAmount(amount);
        this.currentCredit -= (this.currentCredit - amount < 0) ? this.currentCredit : amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber, final GameStateInfo gameState) {
        eventsSubscriber.registerCallback(GameEvent.VICTORY, e -> incrementCredit(gameState.score().getValue()));
    }

    private void validateAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount! It has to be > 0 (Received: '" + amount + "')");
        }
    }
}
