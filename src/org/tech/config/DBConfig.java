package org.tech.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DBConfig { // Singleton Class
    private static Connection conn;
    private static PreparedStatement stmt;
    private static ResultSet rs;
    private static DBConfig db = null;

    private DBConfig() {
        Properties p = new Properties();
        try {
            p.load(PathHelper.fin);
            String driverClassName = p.getProperty("driver.classname");
            String username = p.getProperty("db.username");
            String password = p.getProperty("db.password");
            String url = p.getProperty("db.url");

            // Load the JDBC driver
            Class.forName(driverClassName);
            conn = DriverManager.getConnection(url, username, password);

            if (conn != null) {
                System.out.println("\033[0;32m\033[1m"+"Database is Connected");
            } else {
                System.out.println("Database Is Not Connected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static DBConfig getDBInstance() {
        if (db == null) {
            db = new DBConfig();
        }
        return db;
    }

    public static Connection getConnection() {
        return conn;
    }

    public static PreparedStatement getStatement() {
        return stmt;
    }

    public static ResultSet getResultSet() {
        return rs;
    }
}
