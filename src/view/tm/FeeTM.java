package view.tm;

public class FeeTM {
    private String feeId;
    private double amount;

    public FeeTM() {
    }

    public FeeTM(String feeId, double amount) {
        this.setFeeId(feeId);
        this.setAmount(amount);
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

    @Override
    public String toString() {
        return "FeeTM{" +
                "feeId='" + feeId + '\'' +
                ", amount=" + amount +
                '}';
    }
}
