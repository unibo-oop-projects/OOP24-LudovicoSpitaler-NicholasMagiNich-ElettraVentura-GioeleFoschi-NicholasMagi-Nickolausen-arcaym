package arcaym.model.user.impl;

import java.util.Collections;
import java.util.EnumSet;
import java.util.Set;

import arcaym.common.utils.Optionals;
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

    /* Initial credit */
    private static final int DEFAULT_CREDIT = 0;
    /* Message shown in case of error while loading the user state from file */
    private static final String DEFAULT_SERIALIZATION_ERROR_MSG = "Something went wrong while loading the user state from file!";
    /* Items owned by the user at the beginning of the game */
    private static final Set<GameObjectType> DEFAULT_ITEMS = EnumSet.copyOf(Set.of(
        GameObjectType.USER_PLAYER,
        GameObjectType.COIN,
        GameObjectType.FLOOR,
        GameObjectType.SPIKE));

    private final UserStateSerializer serializer;

    /**
     * Default constructor.
     */
    public UserStateImpl() {
        this.serializer = new UserStateSerializerImpl();
        final var savedState = serializer.load();
        if (savedState.isEmpty()) {
            updateSavedState(new UserStateInfo(DEFAULT_CREDIT, DEFAULT_ITEMS, DEFAULT_ITEMS, Collections.emptySet()));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void unlockNewItem(final GameObjectType gameObject) {
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
        if (savedState.itemsOwned().contains(gameObject) || savedState.purchasedItems().contains(gameObject)) {
            throw new IllegalArgumentException("Cannot unlock an object already owned! (Unlocking: " + gameObject + ")");
        }
        final var itemsOwned = EnumSet.copyOf(savedState.itemsOwned());
        itemsOwned.add(gameObject);
        final var newState = savedState.withItemsOwned(itemsOwned);
        if (savedState.purchasedItems().isEmpty()) {
            updateSavedState(newState.withPurchasedItems(EnumSet.copyOf(Set.of(gameObject))));
        } else {
            final var purchasedItems = EnumSet.copyOf(savedState.purchasedItems());
            purchasedItems.add(gameObject);
            updateSavedState(newState.withPurchasedItems(purchasedItems));
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getCredit() {
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
        return savedState.credit();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getItemsOwned() {
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
        return savedState.itemsOwned();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Set<GameObjectType> getPurchasedItems() {
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
        return savedState.purchasedItems();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        validateAmount(amount);
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
        final var newCredit = savedState.credit() + amount;
        updateSavedState(savedState.withCredit(newCredit));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        validateAmount(amount);
        final var savedState = Optionals.orIllegalState(serializer.load(), DEFAULT_SERIALIZATION_ERROR_MSG);
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
