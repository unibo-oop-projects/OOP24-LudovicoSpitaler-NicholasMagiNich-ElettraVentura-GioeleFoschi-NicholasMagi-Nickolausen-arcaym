package arcaym.controller.user;

import arcaym.model.user.UserStateInfo;

/**
 * Models a collection of read-only operations of {@link UserStateSerializer}.
 */
public interface UserStateSerializerInfo {

    /**
     * Reads the user state from file. 
     * If no user save has been found, a {@link UserStateInfo#getDefaultState()}
     * gets written in the save file and returned from the function.
     * 
     * @return the saved state if it exists, {@link UserStateInfo#getDefaultState()} otherwise.
     */
    UserStateInfo getUpdatedState();
}
