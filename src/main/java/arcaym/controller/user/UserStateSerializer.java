package arcaym.controller.user;

import arcaym.model.user.UserStateInfo;

/**
 * A facade for the serialization & de-serialization of the user state.
 */
public interface UserStateSerializer extends UserStateSerializerInfo {

    /**
     * Serializes the user state.
     * @param userState
     * @return {@code True} if the user state has been saved, {@code False} otherwise.
     */
    boolean save(UserStateInfo userState);
}
