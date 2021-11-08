package model;

public class StudentDetail {
    private String registerId;
    private String classId;
    private String feeId;
    private String collectDate;
    private String collectTime;
    private double amount;


    public StudentDetail() {
    }

    public StudentDetail(String registerId, String classId, String feeId, String collectDate, String collectTime, double amount) {
        this.setRegisterId(registerId);
        this.setClassId(classId);
        this.setFeeId(feeId);
        this.setCollectDate(collectDate);
        this.setCollectTime(collectTime);
        this.setAmount(amount);
    }

    public String getRegisterId() {
        return registerId;
    }

    public void setRegisterId(String registerId) {
        this.registerId = registerId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public String getCollectDate() {
        return collectDate;
    }

    public void setCollectDate(String collectDate) {
        this.collectDate = collectDate;
    }

    public String getCollectTime() {
        return collectTime;
    }

    public void setCollectTime(String collectTime) {
        this.collectTime = collectTime;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "StudentDetail{" +
                "registerId='" + registerId + '\'' +
                ", classId='" + classId + '\'' +
                ", feeId='" + feeId + '\'' +
                ", collectDate='" + collectDate + '\'' +
                ", collectTime='" + collectTime + '\'' +
                ", amount=" + amount +
                '}';
    }
}