package view.tm;

public class UserTM {
    private String userName;
    private String name;
    private String address;
    private String telephoneNumber;
    private String role;


    public UserTM() {
    }

    public UserTM(String userName, String name, String address, String telephoneNumber, String role) {
        this.userName = userName;
        this.name = name;
        this.address = address;
        this.telephoneNumber = telephoneNumber;
        this.role = role;
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
}