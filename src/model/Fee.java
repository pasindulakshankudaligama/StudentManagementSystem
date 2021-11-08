package model;

import java.util.ArrayList;

public class Fee {
    private String feeId;
    private double amount;
    private ArrayList<StudentDetail> students;

    public Fee() {
    }


    public Fee(String feeId, double amount, ArrayList<StudentDetail> students) {
        this.setFeeId(feeId);
        this.setAmount(amount);
        this.setStudents(students);
    }

    public String getFeeId() {
        return feeId;
    }

    public void setFeeId(String feeId) {
        this.feeId = feeId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ArrayList<StudentDetail> getStudents() {
        return students;
    }

    public void setStudents(ArrayList<StudentDetail> students) {
        this.students = students;
    }

    @Override
    public String toString() {
        return "Fee{" +
                "feeId='" + feeId + '\'' +
                ", amount=" + amount +
                ", students=" + students +
                '}';
    }
}
