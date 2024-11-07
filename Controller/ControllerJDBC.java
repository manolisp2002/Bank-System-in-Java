package Controller;

import Model.User;
import java.sql.*;
import java.util.ArrayList;
import Model.Transaction;


public class ControllerJDBC {

    //true - if the transaction was added to the database
    //false - if the transaction was not added to the database
    protected static boolean addTransactiontoDatabase(Transaction transaction) {
        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO transactions (id, user_id, transaction_type, transaction_amount, transaction_date) " +
                            "VALUES (?, ?, ?, ?, NOW())"
            );


            stmt.setString(1, transaction.getTransactionId());
            stmt.setInt(2, transaction.getUserId());
            stmt.setString(3, transaction.getTransactionType());
            stmt.setDouble(4, transaction.getAmount());

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //true-if the balance was updated successfully
    //false-if the balance was not updated
    protected static boolean updateBalance(User user) {
        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

            PreparedStatement stmt = conn.prepareStatement("UPDATE users SET current_balance = ? WHERE id = ?");
            stmt.setDouble(1, user.getBalance());
            stmt.setInt(2, user.getId());

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    //return an arraylist of transactions for the user
    public static ArrayList<Transaction> getTransaction(User user) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions WHERE user_id = ?");
            stmt.setInt(1, user.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction(
                        rs.getInt("user_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("transaction_amount"),
                        rs.getDate("transaction_date")
                );

                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}