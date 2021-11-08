package view.tm;

public class ExamTM {
    private String examId;
    private String subject;

    public ExamTM() {
    }

    public ExamTM(String examId, String subject) {
        this.setExamId(examId);
        this.setSubject(subject);
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId='" + examId + '\'' +
                ", subject='" + subject + '\'' +
                '}';
    }
}
