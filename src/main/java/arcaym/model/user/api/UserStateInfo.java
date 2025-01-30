package arcaym.model.user.api;

/**
 * An interface modelling the a read-only view of {@link UserState}.
 */
public interface UserStateInfo {

    /**
     * 
     * @return the credit the user accumulated 
     */
    int getCredit();
}
