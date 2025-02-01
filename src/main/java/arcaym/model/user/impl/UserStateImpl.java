package arcaym.model.user.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import arcaym.controller.user.api.UserStateSerializer;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.core.engine.api.GameStateInfo;
import arcaym.model.game.core.events.api.EventsSubscriber;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;
import arcaym.model.user.api.UserStateInfo;

/**
 * Implementation of {@link UserState}.
 */
public class UserStateImpl implements UserState {

    private static final int DEFAULT_CREDIT = 0;
    private static final Set<GameObjectType> DEFAULT_ITEMS_OWNED = EnumSet.copyOf(Set.of(
        GameObjectType.USER_PLAYER,
        GameObjectType.COIN,
        GameObjectType.FLOOR,
        GameObjectType.SPIKE));

    private UserStateInfo userStateData;
    private final UserStateSerializer serializer = new UserStateSerializerImpl();

    /**
     * Default constructor.
     */
    public UserStateImpl() {
        var savedState = serializer.load();
        if (savedState.isPresent()) {
            this.userStateData = savedState.get();    
        } else {
            this.userStateData = new UserStateInfo(DEFAULT_CREDIT, DEFAULT_ITEMS_OWNED, DEFAULT_ITEMS_OWNED, Collections.emptySet());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockNewItem(final GameObjectType gameObject) {
        final var purchasedItems = EnumSet.copyOf(userStateData.purchasedItems());
        purchasedItems.add(gameObject);
        updateSavedState(this.userStateData.withPurchasedItems(purchasedItems));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        return this.userStateData.credit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getItemsOwned() {
        return this.userStateData.itemsOwned();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getPurchasedItems() {
        return this.userStateData.purchasedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        validateAmount(amount);
        final var newCredit = this.userStateData.credit() + amount;
        updateSavedState(this.userStateData.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        validateAmount(amount);
        final var newCredit = this.userStateData.credit() - amount;
        updateSavedState(this.userStateData.withCredit(newCredit));
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
