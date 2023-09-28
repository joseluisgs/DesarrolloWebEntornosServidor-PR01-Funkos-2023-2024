package org.example.repositories;

import org.example.database.DatabaseManager;
import org.example.models.Funko;
import org.example.models.Modelo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


public class FunkoRepositoryImpl implements FunkoRepository{
    private static FunkoRepositoryImpl instance;

    private final Logger logger = LoggerFactory.getLogger(FunkoRepositoryImpl.class);
    private final DatabaseManager db;

    private FunkoRepositoryImpl(DatabaseManager db){
        this.db= db;
    }

    public static FunkoRepositoryImpl getInstance(DatabaseManager db){
        if (instance == null){
            instance= new FunkoRepositoryImpl(db);
        }

        return instance;
    }


    public Funko save(Funko funko) throws SQLException {
        String sqlQuery = "INSERT INTO Funko (uuid, nombre, modelo, precio, fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)";
        try (var conn = db.getConnection();
             var stmt = conn.prepareStatement(sqlQuery, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setObject(1, funko.getUuid());
            stmt.setString(2, funko.getNombre());
            stmt.setString(3, funko.getModelo().toString());
            stmt.setDouble(4, funko.getPrecio());
            stmt.setDate(5, Date.valueOf(funko.getFecha_lanzamiento()));
            int res = stmt.executeUpdate();
            conn.commit();

            if (res > 0) {
                ResultSet rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    funko.setId(rs.getInt(1)); // Obtiene el ID generado automáticamente
                }
                rs.close();
            }else{
                logger.error("Objeto no guardado en la base de datos");
                throw new SQLException("Funko con nombre "+ funko.getNombre() + " no almacenado en la BD");
            }
        }catch (SQLException e) {
            logger.error("ERROR: " + e.getMessage(),e);
        }
        return funko;
    }


    public Funko update(Funko funko) throws SQLException {
        logger.info("Actualizando Objeto ..... ");
        String sqlQuery = "UPDATE Funko SET nombre = ? , modelo = ?, precio = ? , updated_at = ? WHERE id = ?";
        try (var conn = db.getConnection();
             var stmt = conn.prepareStatement(sqlQuery);){
            stmt.setString(1,funko.getNombre());
            stmt.setString(2,funko.getModelo().toString());
            stmt.setDouble(3,funko.getPrecio());
            stmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(5,funko.getId());
            var rs = stmt.executeUpdate();
            if (rs <= 0 ){
                logger.error("Funko no encontrado en la BD");
                throw new SQLException("Funko con ID " + funko.getId() + " no encontrado en la BD");
            }

            logger.debug("Objeto Actualizado Correctamente!");
        }

        return funko;
    }


    public Optional<Funko> findById(Integer id) throws SQLException{
        logger.info("Buscando Objeto con ID "+ id + "......");
        String sqlQuery = "SELECT * FROM Funko WHERE id = ?";
        Optional<Funko> funk = Optional.empty();
        try (var conn = db.getConnection(); var stmt = conn.prepareStatement(sqlQuery)){
            stmt.setInt(1,id);
            var rs = stmt.executeQuery();
            while (rs.next()){
                Funko fk = new Funko();
                fk.setId(rs.getInt("id"));
                fk.setUuid((UUID) rs.getObject("uuid"));
                fk.setNombre(rs.getString("name"));
                fk.setModelo(Modelo.valueOf(rs.getString("modelo")));
                fk.setPrecio(rs.getDouble("precio"));
                fk.setFecha_lanzamiento(rs.getDate("fecha_lanzamiento").toLocalDate());
                funk = Optional.of(fk);
            }
        }catch (SQLException e){
            logger.error("ERROR: " + e.getMessage(),e);
        }

        return funk;
    }



    public List<Funko> findAll() throws SQLException{
        logger.info("Obteniendo todos los Objetos");
        String sqlQuery = "SELECT * FROM Funko";
        List<Funko> funks = new ArrayList<>();
        try(var conn = db.getConnection(); var stmt = conn.prepareStatement(sqlQuery)){
            var rs = stmt.executeQuery();
            while (rs.next()){
                Funko fk = new Funko();
                fk.setId(rs.getInt("id"));
                fk.setUuid((UUID) rs.getObject("uuid"));
                fk.setNombre(rs.getString("name"));
                fk.setModelo(Modelo.valueOf(rs.getString("modelo")));
                fk.setPrecio(rs.getDouble("precio"));
                fk.setFecha_lanzamiento(rs.getDate("fecha_lanzamiento").toLocalDate());
                funks.add(fk);
            }
            logger.debug("Objetos Obtenidos Correctamente");
        }catch (SQLException e){
            logger.error("ERROR: " + e.getMessage(),e);
        }
        return funks;
    }


    public boolean deleteById(Integer id) throws SQLException{
        logger.info("Eliminando Objeto con ID " + id + "..........");
        String sqlQuery = "DELETE FROM Funko WHERE id = ? ";
        try (var conn = db.getConnection(); var stmt = conn.prepareStatement(sqlQuery)){
            stmt.setInt(1, id);
            var rs = stmt.executeUpdate();
            if (rs>0){
                logger.info("Eliminado correctamente");
                return true;
            }
        }catch (SQLException e){
            logger.error("ERROR: " + e.getMessage(),e);
        }
        return false;
    }



    public void deleteAll() throws SQLException{
        logger.info("Eliminando Objetos de la BD......");
        String sqlQuery = "DELETE FROM Funko";
        try (var conn = db.getConnection(); var stmt = conn.prepareStatement(sqlQuery)){
            stmt.executeUpdate();
        }catch (SQLException e){
            logger.error("ERROR: " + e.getMessage(),e);
        }
        logger.info("Objetos eliminados Correctamente");
    }



    public List<Funko> findByNombre(String name) throws SQLException{
        logger.info("Obtener Funko con Nombre "+name+".......");
        List<Funko> funks = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Funko WHERE name LIKE ?";
        try (var conn = db.getConnection(); var stmt = conn.prepareStatement(sqlQuery)){
            stmt.setString(1,"%" + name + "%");
            var rs = stmt.executeQuery();
            while (rs.next()){
                Funko fk = new Funko();
                fk.setId(rs.getInt("id"));
                fk.setUuid((UUID) rs.getObject("uuid"));
                fk.setNombre(rs.getString("name"));
                fk.setModelo(Modelo.valueOf(rs.getString("modelo")));
                fk.setPrecio(rs.getDouble("precio"));
                fk.setFecha_lanzamiento(rs.getDate("fecha_lanzamiento").toLocalDate());
                funks.add(fk);
            }
            logger.debug("Objeto obtenido con nombre: " + name);
        }catch (SQLException e) {
            logger.error("ERROR: " + e.getMessage(),e);
        }

        return funks;
    }


}
