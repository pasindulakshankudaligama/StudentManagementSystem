package model;

public class Login {
    private String userName;
    private String loginId;
    private String userPassword;

    public Login() {
    }

    public Login(String userName, String loginId, String userPassword) {
        this.setUserName(userName);
        this.setLoginId(loginId);
        this.setUserPassword(userPassword);
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "Login{" +
                "userName='" + userName + '\'' +
                ", loginId='" + loginId + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
