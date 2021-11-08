package model;

public class ExamDetail {
    private String date;
    private String examId;
    private String registerId;


    public ExamDetail() {
    }

    public ExamDetail(String date, String examId, String registerId) {
        this.setDate(date);
        this.setExamId(examId);
        this.setRegisterId(registerId);
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getExamId() {
        return examId;
    }

    public void setExamId(String examId) {
        this.examId = examId;
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    @Override
    public String toString() {
        return "ExamDetail{" +
                "date='" + date + '\'' +
                ", examId='" + examId + '\'' +
                ", registerId='" + registerId + '\'' +
                '}';
    }
}
