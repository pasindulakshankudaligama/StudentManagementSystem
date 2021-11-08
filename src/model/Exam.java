package model;

import java.util.ArrayList;

public class Exam {
    private String examId;
    private String subject;
    private ArrayList<ExamDetail> exams;

    public Exam() {
    }

    public Exam(String examId, String subject, ArrayList<ExamDetail> exams) {
        this.setExamId(examId);
        this.setSubject(subject);
        this.setExams(exams);
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

    public ArrayList<ExamDetail> getExams() {
        return exams;
    }

    public void setExams(ArrayList<ExamDetail> exams) {
        this.exams = exams;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "examId='" + examId + '\'' +
                ", subject='" + subject + '\'' +
                ", exams=" + exams +
                '}';
    }
}
