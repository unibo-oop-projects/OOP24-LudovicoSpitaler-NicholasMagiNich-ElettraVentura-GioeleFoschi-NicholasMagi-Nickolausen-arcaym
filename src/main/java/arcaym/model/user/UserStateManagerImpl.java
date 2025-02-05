package arcaym.model.user;

import java.util.Set;

import com.google.common.collect.Sets;

import arcaym.controller.user.UserStateSerializer;
import arcaym.controller.user.UserStateSerializerJSON;
import arcaym.model.game.core.engine.GameStateInfo;
import arcaym.model.game.core.events.EventsSubscriber;
import arcaym.model.game.events.GameEvent;
import arcaym.model.game.objects.GameObjectType;

/**
 * Implementation of {@link UserStateManager}.
 */
public class UserStateManagerImpl implements UserStateManager {

    private final UserStateSerializer serializer;

    /**
     * Default constructor.
     */
    public UserStateManagerImpl() {
        this.serializer = new UserStateSerializerJSON();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockNewItem(final GameObjectType gameObject) {
        final var savedState = this.serializer.getUpdatedState();
        if (this.hasItem(gameObject)) {
            throw new IllegalArgumentException("Cannot unlock an object already owned! (Unlocking: " + gameObject + ")");
        }
        this.updateSavedStateOrIllegalState(savedState.withPurchasedItems(Sets.union(
            savedState.purchasedItems(), 
            Set.of(gameObject))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        final var savedState = this.serializer.getUpdatedState();
        return savedState.credit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getItemsOwned() {
        final var savedState = this.serializer.getUpdatedState();
        return savedState.itemsOwned();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasItem(final GameObjectType item) {
        final var savedState = this.serializer.getUpdatedState();
        return savedState.hasItem(item);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getPurchasedItems() {
        final var savedState = this.serializer.getUpdatedState();
        return savedState.purchasedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        this.validateAmount(amount);
        final var savedState = this.serializer.getUpdatedState();
        final var newCredit = savedState.credit() + amount;
        this.updateSavedStateOrIllegalState(savedState.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        this.validateAmount(amount);
        final var savedState = this.serializer.getUpdatedState();
        final var newCredit = savedState.credit() - (savedState.credit() - amount < 0 ? savedState.credit() : amount);
        this.updateSavedStateOrIllegalState(savedState.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEventsCallbacks(final EventsSubscriber<GameEvent> eventsSubscriber, final GameStateInfo gameState) {
        eventsSubscriber.registerCallback(GameEvent.VICTORY, e -> this.incrementCredit(gameState.score().getValue()));
    }

    private void updateSavedStateOrIllegalState(final UserStateInfo newState) {
        if (!this.serializer.save(newState)) {
            throw new IllegalStateException("Cannot save the user state!");
        }
    }

    private void validateAmount(final int amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("Invalid amount! It has to be > 0 (Received: '" + amount + "')");
        }
    }
}
