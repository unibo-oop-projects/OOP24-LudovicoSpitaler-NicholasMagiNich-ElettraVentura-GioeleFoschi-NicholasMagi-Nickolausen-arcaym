package arcaym.controller.user.api;

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
}
