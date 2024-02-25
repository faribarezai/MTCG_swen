package mtcg.dal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DatabaseManager {
    INSTANCE;

<<<<<<< HEAD
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/mtcg";
=======
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432";
>>>>>>> c5eb8b5631d66a05baccdca2c975cc2c8944356c
    private static final String USER = "postgres";
    private static final String PASSWORD = "password";

    public Connection getConnection() {
        try {
            return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
        } catch (SQLException e) {
            throw new DataAccessException("Failed to establish a database connection", e);
        }
    }
}
