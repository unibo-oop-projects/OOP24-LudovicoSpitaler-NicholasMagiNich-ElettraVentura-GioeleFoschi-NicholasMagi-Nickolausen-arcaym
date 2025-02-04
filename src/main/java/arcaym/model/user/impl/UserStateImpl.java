package arcaym.model.user.impl;

import java.util.Set;

import com.google.common.collect.Sets;

import arcaym.controller.user.api.UserStateSerializer;
import arcaym.controller.user.impl.UserStateSerializerJSON;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;

/**
 * Implementation of {@link UserState}.
 */
public class UserStateImpl implements UserState {

    private final UserStateSerializer serializer;

    /**
     * Default constructor.
     */
    public UserStateImpl() {
        this.serializer = new UserStateSerializerJSON();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockNewItem(final GameObjectType gameObject) {
        final var savedState = serializer.getUpdatedState();
        if (savedState.getItemsOwned().contains(gameObject) || savedState.purchasedItems().contains(gameObject)) {
            throw new IllegalArgumentException("Cannot unlock an object already owned! (Unlocking: " + gameObject + ")");
        }
        if (savedState.purchasedItems().isEmpty()) {
            updateSavedState(savedState.withPurchasedItems(Set.of(gameObject)));
        } else {
            final var purchasedItems = Sets.union(savedState.purchasedItems(), Set.of(gameObject));
            updateSavedState(savedState.withPurchasedItems(purchasedItems));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        final var savedState = serializer.getUpdatedState();
        return savedState.credit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getItemsOwned() {
        final var savedState = serializer.getUpdatedState();
        return savedState.getItemsOwned();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getPurchasedItems() {
        final var savedState = serializer.getUpdatedState();
        return savedState.purchasedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        validateAmount(amount);
        final var savedState = serializer.getUpdatedState();
        final var newCredit = savedState.credit() + amount;
        updateSavedState(savedState.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        validateAmount(amount);
        final var savedState = serializer.getUpdatedState();
        final var newCredit = savedState.credit() - (savedState.credit() - amount < 0 ? savedState.credit() : amount);
        updateSavedState(savedState.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber, final GameStateInfo gameState) {
        eventsSubscriber.registerCallback(GameEvent.VICTORY, e -> incrementCredit(gameState.score().getValue()));
    }

    private void updateSavedState(final UserStateInfo newState) {
        serializer.save(newState);
    }

    private void validateAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount! It has to be > 0 (Received: '" + amount + "')");
        }
    }
}
