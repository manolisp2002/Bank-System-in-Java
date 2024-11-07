package Model;

import java.util.Date;

public class Transaction{
    private final String transaction_id;
    private final int userId;
    private final String transactionType;
    private final double amount;
    private final Date transactionDate;


    public Transaction( int userId, String transactionType, double amount, Date transactionDate){

        this.userId = userId;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;

        this.transaction_id = generateUniqueTransactionId(userId);
    }

    //return a unique transaction id
    public static String generateUniqueTransactionId(int userId) {

        long timestamp = System.currentTimeMillis();


        int randomPart = (int)(Math.random() * 10000);  // 4 τυχαία ψηφία


        String transactionId = userId + "" + timestamp + "" + randomPart;

        return transactionId;
    }


    public String getTransactionId() {
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
