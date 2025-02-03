package arcaym.controller.user.api;

import java.util.Optional;

import arcaym.model.user.api.UserStateInfo;

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

    /**
     * Serializes the user state.
     * @param userState
     * @param fileName
     * @return {@code True} if the user state has been saved, {@code False} otherwise.
     */
    boolean save(UserStateInfo userState, String fileName);

    /**
     * De-serializes the user state.
     * @return the UserState previously saved. {@link Optional#empty()} if an error occurred while loading the UserState.
     */
    Optional<UserStateInfo> load();

    /**
     * De-serializes the user state.
     * @param fileName the filename of the save. 
     * @return the UserState previously saved. {@link Optional#empty()} if an error occurred while loading the UserState.
     */
    Optional<UserStateInfo> load(String fileName);
}
