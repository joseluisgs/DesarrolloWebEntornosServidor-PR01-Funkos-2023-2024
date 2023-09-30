
package org.repositories;

import org.models.Funko;
import org.models.Modelo;
import org.services.database.DatabaseManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FunkoRepositoryImp implements FunkoRepository {
    private static FunkoRepositoryImp instance;
    private final Logger logger = LoggerFactory.getLogger(FunkoRepositoryImp.class);

    private final DatabaseManager db;

    private FunkoRepositoryImp(DatabaseManager db) {
        this.db = db;
    }


    public static FunkoRepositoryImp getInstance(DatabaseManager db) {
        if (instance == null) {
            instance = new FunkoRepositoryImp(db);
        }
        return instance;
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        logger.debug("Obteniendo todos los funkos");
        var query = "SELECT * FROM funkos";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            var rs = stmt.executeQuery();
            var lista = new ArrayList<Funko>();
            while (rs.next()) {
                Funko funko = Funko.builder()
                        .id(rs.getLong("ID"))
                        .COD(rs.getObject("cod", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(Modelo.valueOf(rs.getString("modelo")))
                        .precio(rs.getDouble("precio"))
                        .fechaLanzamiento(rs.getObject("fecha_lanzamiento", LocalDate.class))
                        .createdAt(rs.getObject("created_at", LocalDateTime.class))
                        .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                        .build();
                lista.add(funko);
            }
            return lista;
        }
    }

    @Override
    public List<Funko> findByNombre(String nombre) throws SQLException {
        logger.debug("Obteniendo todos los funkos por nombre que contenga: " + nombre);
        String query = "SELECT * FROM funkos WHERE nombre LIKE ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.setString(1, "%" + nombre + "%");
            var rs = stmt.executeQuery();
            var lista = new ArrayList<Funko>();
            while (rs.next()) {
                Funko funko = Funko.builder()
                        .id(rs.getLong("ID"))
                        .COD(rs.getObject("cod", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(Modelo.valueOf(rs.getString("modelo")))
                        .precio(rs.getDouble("precio"))
                        .fechaLanzamiento(rs.getObject("fecha_lanzamiento", LocalDate.class))
                        .createdAt(rs.getObject("created_at", LocalDateTime.class))
                        .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                        .build();
                lista.add(funko);
            }
            return lista;
        }
    }

    @Override
    public Optional<Funko> findById(Long id) throws SQLException {
        logger.debug("Obteniendo el funko con id: " + id);
        String query = "SELECT * FROM funkos WHERE ID = ?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.setLong(1, id);
            var rs = stmt.executeQuery();
            Optional<Funko> funko = Optional.empty();
            while (rs.next()) {
                funko = Optional.of(Funko.builder()
                        .id(rs.getLong("ID"))
                        .COD(rs.getObject("cod", UUID.class))
                        .nombre(rs.getString("nombre"))
                        .modelo(Modelo.valueOf(rs.getString("modelo")))
                        .precio(rs.getDouble("precio"))
                        .fechaLanzamiento(rs.getObject("fecha_lanzamiento", LocalDate.class))
                        .createdAt(rs.getObject("created_at", LocalDateTime.class))
                        .updatedAt(rs.getObject("updated_at", LocalDateTime.class))
                        .build()
                );
            }
            return funko;
        }
    }

    @Override
    public Funko save(Funko funko) throws SQLException  {
        logger.debug("Guardando el funko: " + funko);
        String query = "INSERT INTO funkos (COD, nombre, modelo, precio, fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
        ) {
            funko.setCreatedAt(LocalDateTime.now());
            funko.setUpdatedAt(LocalDateTime.now());
            stmt.setObject(1, funko.getCOD());
            stmt.setString(2, funko.getNombre());
            stmt.setString(3, funko.getModelo().toString());
            stmt.setDouble(4, funko.getPrecio());
            stmt.setObject(5, funko.getFechaLanzamiento());
            var res = stmt.executeUpdate();
           // connection.commit();
            if (res > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                while (rs.next()) {
                    funko.setId(rs.getLong(1));
                }
                rs.close();
            } else {
                logger.error("Funko no guardado");
            }
        }
        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException  {
        logger.debug("Actualizando el funko: " + funko);
        String query = "UPDATE funkos SET nombre =?, modelo =?, precio =?, updated_at =? WHERE ID =?";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            funko.setUpdatedAt(LocalDateTime.now());
            stmt.setString(1, funko.getNombre());
            stmt.setString(2, funko.getModelo().toString());
            stmt.setDouble(3, funko.getPrecio());
            stmt.setObject(4, funko.getUpdatedAt());

            var res = stmt.executeUpdate();
            if (res > 0) {
                logger.debug("Funko actualizado");
            } else {
                logger.error("Funko no actualizado al no encontrarse en la base de datos con id: " + funko.getId());
            }
        }
        return funko;
    }

    @Override
    public boolean deleteById(Long id) throws SQLException {
        logger.debug("Borrando el funko con id: " + id);
        String query = "DELETE FROM funkos WHERE ID =?";
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
        String query = "DELETE FROM funkos";
        try (var connection = db.getConnection();
             var stmt = connection.prepareStatement(query)
        ) {
            stmt.executeUpdate();
        }
    }
}

