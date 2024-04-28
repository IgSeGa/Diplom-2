package site.nomoreparties.stellarburgers.params.responses.user.update;

public class UpdateUser {
    private boolean success;
    private User user;
    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
