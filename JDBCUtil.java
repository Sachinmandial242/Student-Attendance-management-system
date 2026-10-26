package com.attendance.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCUtil {

    private static final String URL =
            "jdbc:mysql://localhost:3306/attendance_db?serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "Sachin@242";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}