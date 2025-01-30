package arcaym.controller.shop.api;

import java.util.Optional;

import arcaym.model.user.api.UserState;

/**
 * A facade for the serialization of the user state.
 */
public interface UserStateSerializer {

    /**
     * Serializes the user state.
     * @param userState
     * @return {@code True} if the user state has been saved, {@code False} otherwise.
     */
    boolean save(UserState userState);

    /**
     * De-serializes the user state.
     * @return the UserState previously saved.
     */
    Optional<UserState> load();
}
