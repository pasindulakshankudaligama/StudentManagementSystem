package view.tm;

public class SubjectTM extends ClassTM{
    private String subjectId;
    private String subjectName;

    public SubjectTM() {
    }

    public SubjectTM(String subjectId, String subjectName) {
        this.setSubjectId(subjectId);
        this.setSubjectName(subjectName);
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    @Override
    public String toString() {
        return "SubjectTM{" +
                "subjectId='" + subjectId + '\'' +
                ", subjectName='" + subjectName + '\'' +
                '}';
    }
}
