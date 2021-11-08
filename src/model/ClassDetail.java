package model;

public class ClassDetail {
    private String teacherId;
    private String subjectId;
    private String classId;
    private String teacherName;

    public ClassDetail() {
    }

    public ClassDetail(String teacherId, String subjectId, String classId, String teacherName) {
        this.setTeacherId(teacherId);
        this.setSubjectId(subjectId);
        this.setClassId(classId);
        this.setTeacherName(teacherName);
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    @Override
    public String toString() {
        return "ClassDetail{" +
                "teacherId='" + teacherId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", classId='" + classId + '\'' +
                ", teacherName='" + teacherName + '\'' +
                '}';
    }
}
