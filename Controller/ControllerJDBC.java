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






}
