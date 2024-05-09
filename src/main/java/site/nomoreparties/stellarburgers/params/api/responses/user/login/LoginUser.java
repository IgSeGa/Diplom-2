package site.nomoreparties.stellarburgers.params.api.responses.user.login;

import site.nomoreparties.stellarburgers.params.api.responses.user.create.User;

public class LoginUser {
    private boolean success;
    private String accessToken;
    private String refreshToken;
    private site.nomoreparties.stellarburgers.params.api.responses.user.create.User user;
    public site.nomoreparties.stellarburgers.params.api.responses.user.create.User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public boolean isSuccess() {
        return success;
    }
    public void setSuccess(boolean success) {
        this.success = success;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
