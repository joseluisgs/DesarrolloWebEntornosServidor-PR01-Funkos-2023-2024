package com.madirex.services.database;

import com.madirex.utils.ApplicationProperties;
import io.github.cdimascio.dotenv.Dotenv;
import lombok.NonNull;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

/**
 * Controlador de Bases de Datos
 */
public class DatabaseManager {
    private static DatabaseManager controller;
    private final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);
    private String serverUrl;
    private String databaseName;
    private String user;
    private String password;
    private String driver;
    private String initScript;
    private Connection connection;
    private PreparedStatement preparedStatement;
    private String connectionUrl;
    private boolean dataInitialized = false;

    /**
     * Constructor privado para Singleton
     */
    private DatabaseManager() {
        initConfig();
    }

    /**
     * Devuelve una instancia del controlador
     *
     * @return instancia del controladorBD
     */
    public static DatabaseManager getInstance() {
        if (controller == null) {
            controller = new DatabaseManager();
        }
        return controller;
    }

    /**
     * Carga la configuración de acceso al servidor de Base de Datos
     */
    private void initConfig() {
        ApplicationProperties properties = ApplicationProperties.getInstance();
        serverUrl = properties.readProperty("db.url", "localhost");
        databaseName = properties.readProperty("db.name", "AppDatabase");
        driver = properties.readProperty("db.driver", "org.h2.Driver");
        initScript = properties.readProperty("db.init", "false");
        Dotenv dotenv = Dotenv.load();
        user = dotenv.get("DATABASE_USER");
        password = dotenv.get("DATABASE_PASSWORD");
        connectionUrl = this.driver + this.serverUrl + File.separator + this.databaseName;
        logger.debug("Configuración de acceso a la Base de Datos cargada");
    }

    /**
     * Abre la conexión con el servidor  de base de datos
     *
     * @throws SQLException Servidor no accesible por problemas de conexión o datos de acceso incorrectos
     */
    public void open() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            return;
        }
        connection = DriverManager.getConnection(connectionUrl, user, password);
        try {
            initData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Inicializa la base de datos con los datos del fichero data.sql
     * Solo si el properties tiene la propiedad db.init en TRUE
     */
    public void initData() throws SQLException, IOException {
        if (!dataInitialized && initScript.equalsIgnoreCase("true")) {
            String sql = new String(Objects.requireNonNull(getClass().getClassLoader()
                    .getResourceAsStream("data.sql")).readAllBytes(), StandardCharsets.UTF_8);
            try (PreparedStatement psTry = connection.prepareStatement(sql)) {
                psTry.execute();
            }
            dataInitialized = true;
        }
    }

    /**
     * Cierra la conexión con el servidor de base de datos
     *
     */
    public void close() {
        try {
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Realiza una consulta a la base de datos de manera "preparada" obteniendo los
     * parámetros opcionales si son necesarios
     *
     * @param querySQL consulta SQL de tipo select
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha podido realizar la consulta o la tabla no existe
     */
    private ResultSet executeQuery(@NonNull String querySQL, Object... params) throws SQLException {
        this.open();
        var strParams = Arrays.toString(params);
        logger.debug("Ejecutando consulta: " + querySQL + " con parámetros: " + strParams);
        preparedStatement = connection.prepareStatement(querySQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Realiza una consulta select a la base de datos de manera "preparada" obteniendo los
     * parámetros opcionales si son necesarios
     *
     * @param querySQL consulta SQL de tipo select
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha podido realizar la consulta o la tabla no existe
     */
    public Optional<ResultSet> select(@NonNull String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Realiza una consulta select a la base de datos de manera "preparada" obteniendo los
     * parámetros opcionales si son necesarios
     *
     * @param querySQL consulta SQL de tipo select
     * @param limit    número de registros de la página
     * @param offset   desplazamiento de registros o número de registros ignorados para comenzar la devolución
     * @param params   parámetros de la consulta parametrizada
     * @return ResultSet de la consulta
     * @throws SQLException No se ha podido realizar la consulta o la tabla no existe o el desplazamiento
     *                      es mayor que el número de registros
     */
    public Optional<ResultSet> select(@NonNull String querySQL, int limit, int offset, Object... params) throws SQLException {
        String query = querySQL + " LIMIT " + limit + " OFFSET " + offset;
        return Optional.of(executeQuery(query, params));
    }

    /**
     * Realiza una consulta de tipo insert de manera "preparada" con los
     * parámetros opcionales si son necesarios
     *
     * @param insertSQL consulta SQL de tipo insert
     * @param params    parámetros de la consulta parametrizada
     * @return Clave del registro insertado
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public Optional<ResultSet> insertAndGetKey(@NonNull String insertSQL, Object... params) throws SQLException {
        this.open();
        var strParams = Arrays.toString(params);
        logger.debug("Ejecutando consulta " + insertSQL + " con parámetros: " + strParams);
        preparedStatement = connection.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }

    /**
     * Realiza una consulta de tipo insert de manera "preparada" con los
     * parámetros opcionales si son necesarios
     *
     * @param insertSQL consulta SQL de tipo insert
     * @param params    parámetros de la consulta parametrizada
     * @return número de registros insertados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public int insert(@NonNull String insertSQL, Object... params) throws SQLException {
        return updateQuery(insertSQL, params);
    }

    /**
     * Realiza una consulta de tipo update de manera "preparada" con los
     * parámetros opcionales si son necesarios
     *
     * @param updateSQL consulta SQL de tipo update
     * @param params    parámetros de la consulta parametrizada
     * @return número de registros actualizados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public int update(@NonNull String updateSQL, Object... params) throws SQLException {
        return updateQuery(updateSQL, params);
    }

    /**
     * Realiza una consulta de tipo delete de manera "preparada" con los
     * parámetros opcionales si son necesarios
     *
     * @param deleteSQL consulta SQL de tipo delete
     * @param params    parámetros de la consulta parametrizada
     * @return número de registros eliminados
     * @throws SQLException tabla no existe o no se ha podido realizar la operación
     */
    public int delete(@NonNull String deleteSQL, Object... params) throws SQLException {
        return updateQuery(deleteSQL, params);
    }

    /**
     * Realiza una consulta de tipo update. Es decir, modifica los datos de manera "preparada" con los
     * parámetros opcionales si son necesarios
     *
     * @param genericSQL consulta SQL de tipo update, delete, create, etc.. que modifica los datos
     * @param params     parámetros de la consulta parametrizada
     * @return número de registros aplicados
     * @throws SQLException no se ha podido realizar la operación
     */
    private int updateQuery(@NonNull String genericSQL, Object... params) throws SQLException {
        this.open();
        var strParams = Arrays.toString(params);
        logger.debug("Ejecutando consulta " + genericSQL + " con parámetros: " + strParams);
        preparedStatement = connection.prepareStatement(genericSQL);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeUpdate();
    }

    /**
     * Inicia la base de datos con el script pasado como parámetro
     *
     * @param genericSQL Script de inicio
     * @return número de registros aplicados
     * @throws SQLException no se ha podido realizar la operación
     */
    public int initSQL(String genericSQL) throws SQLException {
        logger.debug("Datos de inicio: " + genericSQL);
        return updateQuery(genericSQL);
    }

    /**
     * Inicia una transacción
     *
     * @throws SQLException No se ha podido realizar la operación
     */
    private void beginTransaction() throws SQLException {
        connection.setAutoCommit(false);
    }

    /**
     * Commit: Confirma los cambios realizados en la base de datos
     *
     * @throws SQLException No se ha podido realizar la operación
     */
    private void commit() throws SQLException {
        connection.commit();
        connection.setAutoCommit(true);
    }

    /**
     * Rollback: Deshace los cambios realizados en la base de datos
     *
     * @throws SQLException No se ha podido realizar la operación
     */
    private void rollback() throws SQLException {
        connection.rollback();
        connection.setAutoCommit(true);
    }


    /**
     * Inicializa la base de datos con los datos del fichero data.sql
     *
     * @param sqlFile   Fichero con los datos de la base de datos
     * @param logWriter Si se quiere mostrar el log de la ejecución
     * @throws FileNotFoundException No se ha encontrado el fichero
     * @throws SQLException          No se ha podido realizar la operación
     */
    public void initData(@NonNull String sqlFile, boolean logWriter) throws FileNotFoundException, SQLException {
        logger.debug("Inicializando datos de fichero: " + sqlFile + " con logWriter: " + logWriter);
        this.open();
        var sr = new ScriptRunner(connection);
        var reader = new BufferedReader(new FileReader(sqlFile));
        sr.runScript(reader);
    }
}