package Controller;

public class DatabaseConfig {

    private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/bankapp";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "manolis12345";


    protected static String getDbUrl() {
        return DB_URL;
    }

    protected static String getDbUser() {
        return DB_USER;
    }

    protected static String getDbPass() {
        return DB_PASS;
    }
}
