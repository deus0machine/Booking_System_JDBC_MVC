package ru.laba6.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Класс подключения к базе данных
 * */
public class DBHelper {
    URL url = getClass()
            .getResource("/ru/laba6/db.properties");
    static Properties property = new Properties();
    FileInputStream fis;
    static String dbUrl;
    static String login;
    static String password;
    static String dbName;

    {
        try {
            fis = new FileInputStream(url.getFile());
            try {
                property.load(fis);
                dbUrl = property.getProperty("db.url");
                login = property.getProperty("db.login");
                password = property.getProperty("db.pass");
                dbName = property.getProperty("db.name");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    private static String LOGIN = "postgres";
    private static String URL = "jdbc:postgresql://localhost:5432/systemBooking";
    private static String PASSWORD = "18201905";
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, LOGIN, PASSWORD);
    }
    /*public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(dbUrl + dbName, login, password);
    }*/
}
