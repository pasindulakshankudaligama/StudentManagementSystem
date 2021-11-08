package model;

public class Class {
    private String classId;
    private String grade;

    public Class() {
    }

    public Class(String classId, String grade) {
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
        return "Class{" +
                "classId='" + classId + '\'' +
                ", grade='" + grade + '\'' +
                '}';
    }
}
