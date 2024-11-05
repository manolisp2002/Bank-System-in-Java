package Controller;

import Model.User;

import java.sql.*;
import java.util.ArrayList;

import Model.Transaction;


public class ControllerJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankapp";
    private static final String DB_USER   = "root";
    private static final String DB_PASS   = "manolis12345";

    public static AuthenticatorController autController = new AuthenticatorController();

    public static User validateLogin(String username,String password)  {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userId = rs.getInt("id");
                double balance = rs.getDouble("current_balance");
                return new User(userId, username, password, balance);
            }

        }catch (SQLException e) {
            e.printStackTrace();

        }
        return null;
    }

    public static boolean registerUser(String username, String password) {
        try {

            if(!checkUser(username)) {


                Connection conn = DriverManager.getConnection(DB_URL,DB_USER, DB_PASS);

                PreparedStatement stmt = conn.prepareStatement(
                        "INSERT INTO users (username, password, current_balance) VALUES (?, ?, ?)");
                stmt.setString(1, username);
                stmt.setString(2, password);
                stmt.setDouble(3, 0.0);

                stmt.executeUpdate();
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //check if the user in the db
    public static boolean checkUser(String username) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? ");

            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            //query returns no results meaning user does not exist
            if (!rs.next()) {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;

    }


    public static boolean addTransactiontoDatabase(Transaction transaction) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement stmt = conn.prepareStatement(
                    "INSERT INTO transactions (user_id, transaction_type, amount, transaction_date) VALUES (?, ?, ?, NOW())");
            stmt.setInt(1, transaction.getUserId());
            stmt.setString(2, transaction.getTransactionType());
            stmt.setDouble(3, transaction.getAmount());

            stmt.executeUpdate();

            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //true-if the balance was updated successfully
    //false-if the balance was not updated
    public static boolean updateBalance(User user) {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

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


    public static boolean transfer(User user,String receiver,double amount){
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
            stmt.setString(1, receiver);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()){
                //receiver user
                User receiverUser = new User(rs.getInt("id"),
                        receiver,
                        rs.getString("password"),
                        rs.getDouble("current_balance"));

                //create the transaction for the sender
                Transaction transferTransaction = new Transaction(
                        user.getId(),
                        "Transfer",
                        -amount,
                        null);

                //create the transaction for the receiver
                Transaction receiverTransaction = new Transaction(
                        receiverUser.getId(),
                        "Transfer",
                        amount,
                        null);

                //update the balance of the sender
                user.setBalance(user.getBalance() - amount);
                updateBalance(user);

                //update the balance of the receiver
                receiverUser.setBalance(receiverUser.getBalance() + amount);
                updateBalance(receiverUser);

                //add the transaction to the db
                addTransactiontoDatabase(transferTransaction);
                addTransactiontoDatabase(receiverTransaction);

                return true;

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static ArrayList<Transaction> getTransaction(User user) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM transactions WHERE user_id = ?");
            stmt.setInt(1, user.getId());

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                transactions.add(new Transaction(
                        rs.getInt("user_id"),
                        rs.getString("transaction_type"),
                        rs.getDouble("amount"),
                        rs.getDate("transaction_date")));
            }



        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactions;
    }
}