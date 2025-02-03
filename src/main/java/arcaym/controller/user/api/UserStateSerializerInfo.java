package arcaym.controller.user.api;

import java.util.Optional;

import arcaym.model.user.api.UserStateInfo;

/**
 * Models a collection of read-only operations of {@link UserStateSerializer}.
 */
public interface UserStateSerializerInfo {

    /**
     * Same as {@link UserStateSerializer#load()}, but throws an IllegalStateException if an error occurs.
     * 
     * @return same as {@link UserStateSerializer#load()}
     * @see UserStateSerializer#load()
     */
    UserStateInfo getUpdatedState();

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
