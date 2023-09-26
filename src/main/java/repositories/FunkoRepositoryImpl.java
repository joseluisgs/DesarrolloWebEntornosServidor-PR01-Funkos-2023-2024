package repositories;
import exceptions.FunkoException;
import exceptions.FunkoNoEncotradoException;
import models.Funko;
import servicies.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FunkoRepositoryImpl implements FunkoRepository{
    private static FunkoRepositoryImpl instance;
    private final Logger logger = LoggerFactory.getLogger(FunkoRepositoryImpl.class);
    // Base de datos
    private final DatabaseManager db;

    private FunkoRepositoryImpl(DatabaseManager db) {
        this.db = db;
    }
    public static FunkoRepositoryImpl getInstance(DatabaseManager db) {
        if (instance == null) {
            instance = new FunkoRepositoryImpl(db);
        }
        return instance;
    }

    @Override
    public Funko save(Funko funko) throws SQLException, FunkoException {
        logger.debug("Guardando el funko: " + funko);
        String query = "INSERT INTO FUNKOS (cod,nombre,modelo,precio,fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)";

        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            funko.setFecha_lanzamiento(LocalDateTime.now());

            stmt.setString(1, String.valueOf(funko.getCod()));
            stmt.setString(2, funko.getNombre());
            stmt.setString(3, funko.getModelo());
            stmt.setDouble(4, funko.getPrecio());
            stmt.setObject(5, funko.getFecha_lanzamiento());
        }
        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException, FunkoException {
        logger.debug("Actualizando el funko: " + funko);
        String query = "UPDATE FUNKOS SET nombre =?, modelo =?, precio =? WHERE cod =?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            // Vamos a crear los datos de la consultaue necesitamos para insertar automaticos aunque los crea la base de datos
            stmt.setString(1, funko.getNombre());
            stmt.setDouble(3, funko.getPrecio());
            stmt.setString(3, funko.getModelo());
            stmt.setObject(4, funko.getCod());

            var res = stmt.executeUpdate();
            if (res > 0) {
                logger.debug("funko actualizado");
            } else {
                logger.error("funko no actualizado al no encontrarse en la base de datos con id: " + funko.getCod());
                throw new FunkoNoEncotradoException("funko no encontrado con id: " + funko.getCod());
            }
        }
        return funko;
    }

    @Override
    public Optional<Funko> findById(Long id) throws SQLException {
        logger.debug("Obteniendo el funko con id: " + id);
        String query = "SELECT * FROM FUNKOS WHERE id = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            Optional<Funko> funko = Optional.empty();
            while (rs.next()) {

                funko = Optional.of(Funko.builder()
                        .cod(rs.getObject("UUID", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(rs.getString("modelo"))
                        .precio(rs.getDouble("precio"))
                        .fecha_lanzamiento(rs.getObject("fecha_lanzamiento", LocalDateTime.class))
                        .build()
                );
            }
            return funko;
        }
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        logger.debug("Obteniendo todos los funkos");
        var query = "SELECT * FROM FUNKOS";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            var rs = stmt.executeQuery();
            var lista = new ArrayList<Funko>();
            while (rs.next()) {
                Funko funko = Funko.builder()
                        .cod(rs.getObject("UUID", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(rs.getString("modelo"))
                        .precio(rs.getDouble("precio"))
                        .fecha_lanzamiento(rs.getObject("fecha_lanzamiento", LocalDateTime.class))
                        .build();
                lista.add(funko);
            }
            return lista;
        }
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        logger.debug("Borrando el funko con id: " + id);
        String query = "DELETE FROM FUNKOS WHERE id =?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.setLong(1, id);
            var res = stmt.executeUpdate();
            return res > 0;
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        logger.debug("Borrando todos los funkos");
        String query = "DELETE FROM FUNKOS";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.executeUpdate();
        }
    }

    @Override
    public List<Funko> findByNombre(String nombre) throws SQLException {
        logger.debug("Obteniendo todos los funkos por nombre que contenga: " + nombre);
        // Vamos a usar Like para buscar por nombre
        String query = "SELECT * FROM FUNKOS WHERE nombre LIKE ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.setString(1, "%" + nombre + "%");
            var rs = stmt.executeQuery();
            var lista = new ArrayList<Funko>();
            while (rs.next()) {

                Funko funko = Funko.builder()
                        .cod(rs.getObject("UUID", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(rs.getString("modelo"))
                        .precio(rs.getDouble("precio"))
                        .fecha_lanzamiento(rs.getObject("fecha_lanzamiento", LocalDateTime.class))
                        .build();

                lista.add(funko);
            }
            return lista;
        }
    }
}
