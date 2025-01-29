package arcaym.model.user.api;

import java.util.Set;

import arcaym.model.game.objects.api.GameObjectType;

/**
 * Interface modelling the user state and its relative operations.
 * This class serves primarily as collector of useful information.
 */
public interface UserState {

    /**
     * 
     * @return the current user score
     */
    int getScore();

    /**
     * 
     * @return the objects owned by the user
     */
    Set<GameObjectType> getPurchasedGameObjects();

    /**
     * Adds a new game object to the collection of purchased assets.
     * 
     * @param gameObject
     */
    void addNewGameObject(GameObjectType gameObject);

    /**
     * Increments the user score.
     * @param amount the amount to add to the score. If the amount is negative, nothing happens
     */
    void incrementScore(int amount);

    /**
     * Decrements the user score.
     * @param amount the amount to subtract to the score. If the subtraction
     * makes the score drop below 0, then it goes to 0
     */
    void decrementScore(int amount);
}
