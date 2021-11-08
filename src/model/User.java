package model;

public class User {
    private String userName;
    private String name;
    private String address;
    private String telephoneNumber;
    private String role;
    private String userPassword;

    public User() {
    }

    public User(String userName, String name, String address, String telephoneNumber, String role, String userPassword) {
        this.setUserName(userName);
        this.setName(name);
        this.setAddress(address);
        this.setTelephoneNumber(telephoneNumber);
        this.setRole(role);
        this.setUserPassword(userPassword);
    }

    public User(String userName, String userPassword) {
        this.userName = userName;
        this.userPassword = userPassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", telephoneNumber='" + telephoneNumber + '\'' +
                ", role='" + role + '\'' +
                ", userPassword='" + userPassword + '\'' +
                '}';
    }
}
