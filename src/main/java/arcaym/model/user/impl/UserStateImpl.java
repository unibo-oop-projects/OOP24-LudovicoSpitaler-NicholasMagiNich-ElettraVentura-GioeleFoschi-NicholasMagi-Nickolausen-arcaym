package arcaym.model.user.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import arcaym.controller.game.core.api.GameStateInfo;
import arcaym.controller.game.events.api.EventsObserver;
import arcaym.controller.game.events.api.EventsSubscriber;
import arcaym.controller.user.api.UserStateSerializer;
import arcaym.controller.user.impl.UserStateSerializerImpl;
import arcaym.model.game.events.api.GameEvent;
import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;

/**
 * Implementation of {@link UserState}.
 */
public class UserStateImpl implements UserState, EventsObserver<GameEvent> {

    private static final int DEFAULT_CREDIT = 0;
    private static final Set<GameObjectType> DEFAULT_ITEMS_OWNED = Set.of(
        GameObjectType.USER_PLAYER, GameObjectType.COIN, GameObjectType.FLOOR, GameObjectType.SPIKE 
    );
    private int currentCredit;
    private final Set<GameObjectType> itemsOwned;
    private final UserStateSerializer serializer = new UserStateSerializerImpl();

    /**
     * Default constructor.
     * 
     * @param itemsOwned
     */
    public UserStateImpl(final Set<GameObjectType> itemsOwned) {
        var savedState = serializer.load();
        if (savedState.isPresent()) {
            this.currentCredit = savedState.get().getCredit();
            this.itemsOwned = savedState.get().getPurchasedGameObjects();
        } else {
            this.currentCredit = DEFAULT_CREDIT;
            this.itemsOwned = DEFAULT_ITEMS_OWNED;
        }
    }

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
    public void unlockNewGameObject(final GameObjectType gameObject) {
        this.itemsOwned.add(gameObject);
        updateSave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementCredit(final int amount) {
        validateAmount(amount);
        this.currentCredit += amount;
        updateSave();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementCredit(final int amount) {
        validateAmount(amount);
        this.currentCredit -= (this.currentCredit - amount < 0) ? this.currentCredit : amount;
        updateSave();
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

    private void updateSave() {
        serializer.save(this);
    }
}
