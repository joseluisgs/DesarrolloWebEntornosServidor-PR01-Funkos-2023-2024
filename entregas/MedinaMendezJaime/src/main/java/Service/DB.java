package Service;

import lombok.Getter;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

@Getter
public class DB implements AutoCloseable {
    private static DB instance;
    private static final String propertiesPath = "src" + File.separator + "main" + File.separator + "resources" + File.separator + "database.properties";
    private Connection connection;

    private DB() {
        openConnection();
    }

    public static DB getInstance() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver: " + e.getMessage());
        }
        if (instance == null) {
            instance = new DB();
        }
        return instance;
    }

    public void openConnection() {
        try {
            Properties properties = new Properties();
            properties.load(new FileReader(propertiesPath));
            String url = properties.getProperty("db.url");
            String user = properties.getProperty("db.user");
            String password = properties.getProperty("db.password");
            String init = properties.getProperty("db.init");
            connection = DriverManager.getConnection(url, user, password);

            Reader reader = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(init).getPath()));
            ScriptRunner scriptRunner = new ScriptRunner(connection);
            scriptRunner.runScript(reader);

        } catch (SQLException e) {
            System.out.println("Error al conectar con la base de datos: " + e.getMessage());
        } catch (IOException e) {
            System.out.println("Error al cargar el archivo de propiedades: " + e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        connection.close();
    }
}
