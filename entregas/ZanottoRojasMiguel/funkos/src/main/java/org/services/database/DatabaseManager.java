package org.services.database;

import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseManager implements AutoCloseable {
    private static DatabaseManager instance;
    private boolean databaseInitTables = false;
    private String databaseUrl;
    private String databaseInitScript;
    private Connection conn;

    private DatabaseManager() {
        loadProperties();
        try {
            openConnection();
            if (databaseInitTables) {
                initTables();
            }
        } catch (SQLException e) {
            e.getMessage();
        }
    }

    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private void loadProperties() {
        try {
            var file = ClassLoader.getSystemResource("config.properties").getFile();
            var props = new Properties();
            props.load(new FileReader(file));
            databaseUrl = props.getProperty("database.connectionUrl", "jdbc:h2:./Funkos");
            databaseInitTables = Boolean.parseBoolean(props.getProperty("database.initTables", "false"));
            databaseInitScript = props.getProperty("database.initScript", "init.sql");
        } catch (IOException e) {
             e.getMessage();
        }
    }

    private void openConnection() throws SQLException {
        conn = DriverManager.getConnection(databaseUrl);
    }

    private void closeConnection() throws SQLException {
        conn.close();
    }

    private void initTables() {
        try {
            executeScript(databaseInitScript, true);
        } catch (FileNotFoundException e) {
           e.getMessage();
        }
    }


    public void executeScript(String scriptSqlFile, boolean logWriter) throws FileNotFoundException {
        ScriptRunner sr = new ScriptRunner(conn);
        var file = ClassLoader.getSystemResource(scriptSqlFile).getFile();
        Reader reader = new BufferedReader(new FileReader(file));
        sr.runScript(reader);
    }


    public Connection getConnection() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                openConnection();
            } catch (SQLException e) {
                e.getMessage();
                throw e;
            }
        }
        return conn;
    }

    @Override
    public void close() throws Exception {
        closeConnection();
    }
}