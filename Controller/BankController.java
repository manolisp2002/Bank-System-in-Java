package Controller;

import Model.Transaction;
import Model.User;
import java.sql.*;


public class BankController {

    //true if the deposit was successful
    //false if the deposit was unsuccessful
    private static boolean handleDeposit(User user, double amountVal) {
        Transaction transaction = new Transaction(user.getId(), "Deposit", amountVal, null);

        // Update user balance
        user.setBalance(user.getBalance() + amountVal);

        // Store transaction and update balance in the database
        return ControllerJDBC.addTransactiontoDatabase(transaction) && ControllerJDBC.updateBalance(user);
    }

    //true if the withdrawal was successful
    //false if the withdrawal was unsuccessful
    private static boolean handleWithdrawal(User user, double amountVal) {
        if (user.getBalance() >= amountVal) {
            Transaction transaction = new Transaction(user.getId(), "Withdrawal", -amountVal, null );


            // Update user balance
            user.setBalance(user.getBalance() - amountVal);
            boolean flag1 = ControllerJDBC.addTransactiontoDatabase(transaction);
            boolean flag2 = ControllerJDBC.updateBalance(user);


            // Store transaction and update balance in the database
            return flag2 && flag1;
        } else {

            return false;
        }
    }

    //true if the transaction was successful
    //false if the transaction was unsuccessful
    public static boolean handleTransaction(User user, String transactionType, double amountVal) {

        if (transactionType.equalsIgnoreCase("Deposit")) {
            return handleDeposit(user, amountVal);
        } else if (transactionType.equalsIgnoreCase("Withdraw")) {

            return handleWithdrawal(user, amountVal);
        }
        return false;
    }

    //true if the transfer was successful
    //false if the transfer was unsuccessful
    public static boolean handleTransfer(User user,String receiver,double amount){

        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

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
                ControllerJDBC.updateBalance(user);
                //update the balance of the receiver
                receiverUser.setBalance(receiverUser.getBalance() + amount);
                ControllerJDBC.updateBalance(receiverUser);

                //add the transaction to the db
                ControllerJDBC.addTransactiontoDatabase(transferTransaction);
                ControllerJDBC.addTransactiontoDatabase(receiverTransaction);



                return true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
