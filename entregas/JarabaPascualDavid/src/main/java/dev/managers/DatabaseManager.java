package dev.managers;

import dev.models.SqlCommand;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.*;
import java.util.Properties;

public class DatabaseManager {

    private final boolean fromProperties = false;
    private final String url;
    private final String dataBaseName;
    private final String username;
    private final String password;

    private final String initScript;

    private Connection connection;
    private PreparedStatement preparedStatement;

    private static DatabaseManager instance;

    private DatabaseManager() throws IOException, SQLException {

        Properties appProps = new Properties();
        appProps.load(new FileInputStream(getClass().getClassLoader().getResource("application.properties").getPath()));

        username = appProps.getProperty("db.username");
        password = appProps.getProperty("db.password");
        dataBaseName = appProps.getProperty("db.name");
        initScript = appProps.getProperty("db.initScript");

        url = "jdbc:h2:mem:"+dataBaseName+";DB_CLOSE_DELAY=-1";

        open();

        if (appProps.getProperty("db.loadTables").equals("true")){
            System.out.println("Ejecutando SQL");

            Reader reader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(initScript).getPath()));
            ScriptRunner sr = new ScriptRunner(connection);
            sr.runScript(reader);
        }

    }


    public void open() throws SQLException {

        connection = DriverManager.getConnection(url, username, password);

    }


    public static DatabaseManager getInstance(){
        if (instance == null){
            try {
                instance = new DatabaseManager();
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return instance;

    }


    public ResultSet executeQuery(SqlCommand sqlCommand) throws SQLException, IOException {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand.getCommand());

        for (int i = 0; i < sqlCommand.getParams().size(); i++) {
            preparedStatement.setObject(i + 1, sqlCommand.getParams().get(i));
        }

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;

    }

    public int executeUpdate(SqlCommand sqlCommand) throws SQLException, IOException {
        open();
        PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand.getCommand());

        for (int i = 0; i < sqlCommand.getParams().size(); i++) {
            preparedStatement.setObject(i + 1, sqlCommand.getParams().get(i));
        }

        int resultSet = preparedStatement.executeUpdate();
        return resultSet;

    }


    public void close() throws SQLException {
        connection.close();
    }
}




