package model;

import java.util.List;

public class Teacher {
    private String teacherId;
    private String teacherName;
    private String address;
    private int phoneNumber;
    private List<ClassDetail> classes;


    public Teacher() {
    }


    public Teacher(String teacherId, String teacherName, String address, int phoneNumber, List<ClassDetail> classes) {
        this.setTeacherId(teacherId);
        this.setTeacherName(teacherName);
        this.setAddress(address);
        this.setPhoneNumber(phoneNumber);
        this.setClasses(classes);
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

    public List<ClassDetail> getClasses() {
        return classes;
    }

    public void setClasses(List<ClassDetail> classes) {
        this.classes = classes;
    }

    @Override
    public String toString() {
        return "Teacher{" +
                "teacherId='" + teacherId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", address='" + address + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", classes=" + classes +
                '}';
    }
}