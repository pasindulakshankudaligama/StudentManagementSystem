package model;

public class RegisterStudent {
    private String studentId;
    private String studentName;

    public RegisterStudent() {
    }

    public RegisterStudent(String studentId, String studentName) {
        this.setStudentId(studentId);
        this.setStudentName(studentName);
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Override
    public String toString() {
        return "RegisterStudent{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
