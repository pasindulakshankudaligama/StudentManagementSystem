package view.tm;

public class RegisterStudentTM {
    private String studentId;
    private String studentName;

    public RegisterStudentTM() {
    }

    public RegisterStudentTM(String studentId, String studentName) {
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
        return "RegisterStudentTM{" +
                "studentId='" + studentId + '\'' +
                ", studentName='" + studentName + '\'' +
                '}';
    }
}
