package database.manager;

import database.models.SqlCommand;
import lombok.extern.log4j.Log4j2;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

@Log4j2
public class DatabaseManager implements AutoCloseable {
    private static DatabaseManager instance;
    private final String filePath;
    private final String username;
    private final String password;

    public static DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    private Connection connection;

    private DatabaseManager() {
        String file = ClassLoader.getSystemResource("application.properties").getFile();
        Properties properties = new Properties();
        try (FileReader reader = new FileReader(file)) {
            properties.load(reader);
        } catch (IOException e) {
            log.error("Error while reading properties file", e);
        }
        filePath = properties.getProperty("db.filePath");
        username = properties.getProperty("db.username");
        password = properties.getProperty("db.password");
        open();
        String initScriptName = properties.getProperty("db.initScript");
        log.info("Iniciando la base de datos");
        String initScript = ClassLoader.getSystemResource(initScriptName).getFile();
        ScriptRunner scriptRunner = new ScriptRunner(connection);
        try {
            FileReader reader = new FileReader(initScript);
            scriptRunner.runScript(reader);
        } catch (FileNotFoundException e) {
            log.error("Error al leer el script de inicialización", e);
        } finally {
            try {
                close();
            } catch (SQLException e) {
                log.error("Error al cerrar la conexión", e);
            }
        }
    }

    public void open() {
        try {
            String connectionString = "jdbc:h2:" + filePath;
            connection = DriverManager.getConnection(connectionString, username, password);
        } catch (Exception e) {
            log.error("Error al abrir la conexión", e);
        }
    }

    private PreparedStatement prepareStatement(SqlCommand sqlCommand) throws SQLException {

        PreparedStatement preparedStatement = connection.prepareStatement(sqlCommand.getCommand());

        for (int i = 0; i < sqlCommand.getParams().size(); i++) {
            preparedStatement.setObject(i + 1, sqlCommand.getParams().get(i));
        }

        return preparedStatement;
    }

    public ResultSet executeQuery(SqlCommand sqlCommand) throws SQLException, IOException {
        open();
        PreparedStatement preparedStatement = prepareStatement(sqlCommand);
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet;

    }

    public int executeUpdate(SqlCommand sqlCommand) throws SQLException, IOException {
        open();
        PreparedStatement preparedStatement = prepareStatement(sqlCommand);
        int affectedRows = preparedStatement.executeUpdate();
        return affectedRows;

    }
    @Override
    public void close() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        } else {
            log.warn("La conexión ya estaba cerrada");
        }
    }
}
