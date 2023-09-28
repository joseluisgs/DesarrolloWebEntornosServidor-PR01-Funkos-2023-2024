package org.funkos.services.database;

import org.apache.ibatis.jdbc.ScriptRunner;
import org.funkos.repositories.funko.FunkoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.sql.*;
import java.util.Optional;
import java.util.Properties;

public class DataBaseManager {

    private static DataBaseManager instance;
    private Connection conn;

    private String port;
    private String name;
    private String connectionUrl;
    private String driver;

    private boolean initTables;

    private PreparedStatement preparedStatement;

    private String initScript;

    private static Logger logger = LoggerFactory.getLogger(DataBaseManager.class);


    private DataBaseManager(){
        initConfig();
    }

    public static DataBaseManager getInstance(){
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }


    public void initConfig() {
        String propertiesFile = ClassLoader.getSystemResource("database.properties").getFile();
        Properties prop = new Properties();

        try{

            prop.load(new FileInputStream(propertiesFile));
            port = prop.getProperty("database.port");
            name = prop.getProperty("database.username");
            connectionUrl = prop.getProperty("database.url");
            driver = prop.getProperty("database.driver");
            initTables = Boolean.parseBoolean(prop.getProperty("database.initTables"));
            initScript = prop.getProperty("database.initScript");
            logger.debug("Obteniendo datos de configuracion de la base de datos {}", connectionUrl);
            if(initTables){
                logger.debug("Inicializando base de datos");
                String file_init =  ClassLoader.getSystemResource(initScript).getFile();
                this.initData(file_init, false);
            }

        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void openConnection() throws SQLException {
        logger.debug("Abriendo conexión en la base de datos {}", connectionUrl);
        if (conn != null && !conn.isClosed()) {
            return;
        }
        conn = DriverManager.getConnection(connectionUrl);
    }

    public void closeConnection() {
        logger.debug("Cerrando conexión de la base de datos {}", connectionUrl);
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                logger.error("No se pudo cerrar la conexión a la base de datos");
                throw new RuntimeException(e);
            }
        }
    }

    private ResultSet executeQuery(String querySQL, Object... params) throws SQLException {
        this.openConnection();
        preparedStatement = conn.prepareStatement(querySQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    public Optional<ResultSet> select(String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    public Optional<ResultSet> select(String querySQL, int limit, int offset, Object... params) throws SQLException {
        String query = querySQL + " LIMIT " + limit + " OFFSET " + offset;
        return Optional.of(executeQuery(query, params));
    }

    public Optional<ResultSet> insert(String insertSQL, Object... params) throws SQLException {

        preparedStatement = conn.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }

    public int update(String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    public int delete(String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    private int updateQuery(String genericSQL, Object... params) throws SQLException {
        preparedStatement = conn.prepareStatement(genericSQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    private void initData(String sqlFile, boolean logWriter) throws SQLException {
        this.openConnection();
        var sr = new ScriptRunner(conn);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(sqlFile));
        } catch (FileNotFoundException e) {
            logger.error("Error al encontrar el script");
            throw new RuntimeException(e);
        }
        if (logWriter) {
            sr.setLogWriter(new PrintWriter(System.out));
        } else {
            sr.setLogWriter(null);
        }
        sr.runScript(reader);
    }
}
