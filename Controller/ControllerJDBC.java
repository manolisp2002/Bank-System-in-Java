package Controller;

import Model.User;

import java.sql.*;


public class ControllerJDBC {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/bankapp";
    private static final String USER   = "root";
    private static final String PASS   = "manolis12345";

    public static AuthenticatorController autController = new AuthenticatorController();

    public static User validateLogin(String username,String password)  {
        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

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


                Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
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
