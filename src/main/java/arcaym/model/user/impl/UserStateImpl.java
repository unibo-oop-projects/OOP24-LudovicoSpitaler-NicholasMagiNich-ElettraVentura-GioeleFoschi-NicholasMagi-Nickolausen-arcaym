package arcaym.model.user.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsObserver;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;

public class UserStateImpl implements UserState, Serializable, EventsObserver<GameEvent> {

    private static final long serialVersionUID = 1L;
    private int currentScore;
    private Set<GameObjectType> itemsOwned;

    /**
     * {@inheritDoc}
     */
    @Override
    public int getScore() {
        return this.currentScore;
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
    public void addNewGameObject(GameObjectType gameObject) {
        this.itemsOwned.add(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementScore(int amount) {
        this.currentScore += Integer.max(amount, 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementScore(int amount) {
        this.currentScore -= (this.currentScore - amount < 0) ? this.currentScore : amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(EventsSubscriber<GameEvent> eventsSubscriber, GameStateInfo gameState) {
        eventsSubscriber.registerCallback(GameEvent.VICTORY, e -> incrementScore(gameState.score().getValue()));
    }
}
