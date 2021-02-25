package dev.bennett.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionUtil {


    public static Connection createConnection(){
        String details = System.getenv("CONN_DETAILS");

        try {
            // factory returns connection object (in this case postgresql)
            Connection conn = DriverManager.getConnection(details);
            return  conn;
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
            return null;
        }
    }
}
