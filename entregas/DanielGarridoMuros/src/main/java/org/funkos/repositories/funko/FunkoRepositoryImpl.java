package org.funkos.repositories.funko;

import org.funkos.enums.Modelo;
import org.funkos.models.Funko;
import org.funkos.services.backup.Backup;
import org.funkos.services.database.DataBaseManager;
import org.funkos.services.funkos.FunkoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FunkoRepositoryImpl implements FunkoRepository{
    private Backup<Funko> backup;

    private DataBaseManager dataBaseManager;
    private static FunkoRepositoryImpl instance;

    private static Logger logger = LoggerFactory.getLogger(FunkoRepositoryImpl.class);


    private FunkoRepositoryImpl(DataBaseManager database, Backup backup){
        dataBaseManager = database;
        this.backup = backup;
    }

    public static FunkoRepositoryImpl getInstance(DataBaseManager database, Backup backup){
        if(instance == null){
            instance = new FunkoRepositoryImpl(database, backup);
        }
        return instance;
    }

    @Override
    public Funko save(Funko funko) throws SQLException, SQLException {
        dataBaseManager.openConnection();
        logger.debug("Insertando funko {} en base de datos", funko.getCOD());
        String sql = "INSERT INTO funkos (COD, nombre, modelo, precio, fecha_lanzamiento, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Optional<ResultSet> result = dataBaseManager.insert(sql, funko.getCOD().toString(), funko.getNombre(), funko.getModelo().toString(), funko.getPrecio(), funko.getFecha().toString(), Timestamp.valueOf(funko.getCreated_at()), Timestamp.valueOf(funko.getUpdated_at()) );
        if(result.isPresent()){
            logger.debug("El funko {} se inserto correctamente", funko.getCOD());
            result.get().next();
            funko.setId(result.get().getInt(1));
        }else{
            logger.error("El funko {} no se inserto correctamente", funko.getCOD() );
        }
        dataBaseManager.closeConnection();
        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException, SQLException {
        dataBaseManager.openConnection();
        logger.debug("Acutalizando funko {} en base de datos", funko.getCOD());
        String sql = "UPDATE funkos SET nombre = ?, modelo = ?, precio = ?, fecha_lanzamiento = ?, updated_at = ? WHERE COD = ?";
        int updated_funko_id = dataBaseManager.update(sql, funko.getNombre(), funko.getModelo().toString(), funko.getPrecio(), funko.getFecha().toString(), funko.getCOD().toString(), funko.getUpdated_at().toString());

        if(updated_funko_id > 0){
            logger.debug("El funko {} se actualizo correctamente", funko.getCOD());
        }else{
            logger.error("El funko {} no se actualizo correctamente", funko.getCOD());
        }

        dataBaseManager.closeConnection();
        return null;
    }

    @Override
    public Optional<Funko> findById(Integer id) throws SQLException {
        dataBaseManager.openConnection();
        logger.debug("Buscando funko por id {}", id);
        String sql = "SELECT * FROM funkos WHERE id = ?";
        var result = dataBaseManager.select(sql, id).orElseThrow(() -> new SQLException("Error al obtener la medicion por id"));

        if(result.next()){
            logger.debug("Funko {} obtenido con exito", result.getInt("id"));
            Funko funko = Funko.builder()
                    .id(result.getInt("id"))
                    .COD(UUID.fromString(result.getString("COD")))
                    .nombre(result.getString("nombre"))
                    .modelo(Modelo.valueOf(result.getString("modelo")))
                    .precio(result.getDouble("precio"))
                    .fecha(LocalDate.parse(result.getString("fecha_lanzamiento")))
                    .created_at(new Timestamp(result.getTimestamp("created_at").getTime()).toLocalDateTime())
                    .updated_at(new Timestamp(result.getTimestamp("updated_at").getTime()).toLocalDateTime())
                    .build();

            return Optional.of(funko);
        }else{
            logger.error("Funko {} no existe en la base de datos", id);
            return Optional.ofNullable(null);
        }

    }

    @Override
    public List<Funko> findAll() throws SQLException {
        dataBaseManager.openConnection();
        logger.debug("Obteniendo todos los funkos de la base de datos");
        String sql = "SELECT * FROM funkos";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todas las mediciones"));
        List<Funko> funkos = new ArrayList<>();

        while (result.next()){
            Funko funko = Funko.builder()
                    .id(result.getInt("id"))
                    .COD(UUID.fromString(result.getString("COD")))
                    .nombre(result.getString("nombre"))
                    .modelo(Modelo.valueOf(result.getString("modelo")))
                    .precio(result.getDouble("precio"))
                    .fecha(LocalDate.parse(result.getString("fecha_lanzamiento")))
                    .created_at(new Timestamp(result.getTimestamp("created_at").getTime()).toLocalDateTime())
                    .updated_at(new Timestamp(result.getTimestamp("updated_at").getTime()).toLocalDateTime())
                    .build();

            funkos.add(funko);
        }

        dataBaseManager.closeConnection();
        return funkos;

    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        dataBaseManager.openConnection();
        logger.debug("Borrando el funko con id: {}", id);
        String sql = "DELETE FROM funkos WHERE id = ?";
        int deleted_funko = dataBaseManager.delete(sql, id);
        if(deleted_funko > 0){
            logger.debug("El funko con id {} fue borrado", id);
        }else{
            logger.error("El funko con id: {} no se pudo eliminar", id);
        }
        dataBaseManager.closeConnection();
        return false;
    }

    @Override
    public void deleteAll() throws SQLException {
        dataBaseManager.openConnection();
        logger.debug("Borrando todos los funkos");
        String sql = "DELETE FROM funkos";
        dataBaseManager.delete(sql);
        dataBaseManager.closeConnection();
    }


    @Override
    public Optional<Funko> findByNombre(String nombre) throws SQLException {
        dataBaseManager.openConnection();
        logger.debug("Buscando funko por el nombre {}", nombre);
        String sql = "SELECT * FROM funkos WHERE nombre = ?";
        var result = dataBaseManager.select(sql, nombre).orElseThrow(() -> new SQLException("Error al obtener la medicion por nombre"));

        result.next();
        Funko funko = Funko.builder()
                .id(result.getInt("id"))
                .COD(UUID.fromString(result.getString("COD")))
                .nombre(result.getString("nombre"))
                .modelo(Modelo.valueOf(result.getString("modelo")))
                .precio(result.getDouble("precio"))
                .fecha(LocalDate.parse(result.getString("fecha_lanzamiento")))
                .build();
        return Optional.of(funko);
    }

    public void backup() throws SQLException, IOException {
        logger.debug("Realizando backup de todos los funkos");
        backup.backup(this.findAll());
    }

    public void restore() throws SQLException, IOException {
        logger.debug("Reseteando backup y borrando todos los funkos de la base de datos");
        var personas = backup.restore();
        this.deleteAll();

    }


}
