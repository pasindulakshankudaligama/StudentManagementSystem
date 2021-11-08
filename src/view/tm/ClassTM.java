package view.tm;

public class ClassTM {
    private String classId;
    private String grade;

    public ClassTM() {
    }

    public ClassTM(String classId, String grade) {
        this.setClassId(classId);
        this.setGrade(grade);
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return "ClassTM{" +
                "classId='" + classId + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
