package Controller;

import Model.User;
import java.sql.*;


public class AuthenticatorController {


    //return the user if the login was successful
    public static User authenticateLogin(String username,String password)  {
        try {
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

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

    //true-if the user was registered successfully
    public static boolean authenticateRegister(String username, String password) {
        try {

            if(!checkUser(username)) {
                Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

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
            Connection conn = DriverManager.getConnection(DatabaseConfig.getDbUrl(), DatabaseConfig.getDbUser(), DatabaseConfig.getDbPass());

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
}
