package uk.ac.tees.aad.w9575245.favfood.maps.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class LoggedInInfo {

    private String userId;
    private String displayName;


    public LoggedInInfo(String userId, String displayName) {
        this.userId = userId;
        this.displayName = displayName;
    }


    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }
}