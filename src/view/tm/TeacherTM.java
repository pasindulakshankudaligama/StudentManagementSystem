package view.tm;

import model.ClassDetail;

public class TeacherTM extends SubjectTM{
    private String teacherId;
    private String teacherName;
    private String address;
    private int phoneNumber;

    public TeacherTM() {
    }

    public TeacherTM(String teacherId, String teacherName, String address, int phoneNumber) {
        this.setTeacherId(teacherId);
        this.setTeacherName(teacherName);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    @Override
    public String toString() {
        return "TeacherTM{" +
                "teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
