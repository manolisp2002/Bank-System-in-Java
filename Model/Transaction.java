package Model;

import java.util.Date;

public class Transaction{
    private final int transaction_id;
    private final int userId;
    private final String transactionType;
    private final double amount;
    private final Date transactionDate;

    public Transaction(int userId, String transactionType, double amount, Date transactionDate){
        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
        // id created with user id number + a random 2 digit number as string values
        this.transaction_id =  Integer.parseInt(String.valueOf(userId) + (int)(Math.random() * 100));
    }

    public int getTransactionId() {
        return transaction_id;
    }

    public int getUserId() {
        return userId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public double getAmount() {
        return amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }
}
