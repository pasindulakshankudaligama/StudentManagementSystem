package view.tm;

public class CoordinatorTM {
    private String studentId;
    private String registerId;
    private String studentNAme;
    private String address;
    private int phoneNumber;
    private int dateOfBirth;
    private int age;

    public CoordinatorTM() {
    }

    public CoordinatorTM(String studentId, String registerId, String studentNAme, String address, int phoneNumber, int dateOfBirth, int age) {
        this.setStudentId(studentId);
        this.setRegisterId(registerId);
        this.setStudentNAme(studentNAme);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setDateOfBirth(dateOfBirth);
        this.setAge(age);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getStudentNAme() {
        return studentNAme;
    }

    public void setStudentNAme(String studentNAme) {
        this.studentNAme = studentNAme;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(int dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "CoordinatorTM{" +
                "studentId='" + studentId + '\'' +
                ", registerId='" + registerId + '\'' +
                ", studentNAme='" + studentNAme + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
