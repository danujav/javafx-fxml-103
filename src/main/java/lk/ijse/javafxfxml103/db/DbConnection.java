package lk.ijse.javafxfxml103.db;

/*
    @author DanujaV
    @created 9/10/23 - 9:26 AM   
*/

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static DbConnection dbConnection;
    private static Connection con;

    private DbConnection() throws SQLException {
        con = DriverManager.getConnection("jdbc:mysql://localhost:3306/kade", "root", "Danu25412541@");
    }

    public static DbConnection getInstance() throws SQLException {
        return (null == dbConnection) ? dbConnection = new DbConnection() : dbConnection;
    }

    public Connection getConnection() {
        return con;
    }
}
