package arcaym.model.user.impl;

import java.io.Serializable;
import java.util.Collections;
import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;
import arcaym.model.user.api.UserState;

public class UserStateImpl implements UserState, Serializable {

    private static final long serialVersionUID = 1L;
    private int currentScore;
    private Set<GameObjectType> unlockedObjects;

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
        return Collections.unmodifiableSet(unlockedObjects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addNewGameObject(GameObjectType gameObject) {
        this.unlockedObjects.add(gameObject);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void incrementScore(int amount) {
        this.currentScore += amount;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void decrementScore(int amount) {
        this.currentScore -= (this.currentScore - amount < 0) ? this.currentScore : amount;
    }
}
