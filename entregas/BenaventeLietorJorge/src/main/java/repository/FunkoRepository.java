package repository;

import database.manager.DatabaseManager;
import database.models.Funko;
import database.models.FunkoDB;
import database.models.SqlCommand;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Log4j2
public class FunkoRepository implements IFunkoRepository {
    private final DatabaseManager databaseManager;
    private static FunkoRepository instance;
    private static final int MAX_SIZE = 25;

    private LinkedHashMap<Integer, FunkoDB> cache = new LinkedHashMap<>(25, 0.75f, true) {
        @Override
        protected boolean removeEldestEntry(Map.Entry<Integer, FunkoDB> eldest) {
            return size() > MAX_SIZE;
        }
    };

    public static FunkoRepository getInstance(DatabaseManager databaseManager) {
        if (instance == null) {
            instance = new FunkoRepository(databaseManager);
        }
        return instance;
    }

    private FunkoRepository(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public FunkoDB save(Funko entity) {
        SqlCommand command = new SqlCommand("INSERT INTO funkos (cod, nombre, modelo, precio, fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)");
        command.addParam(entity.getCod());
        command.addParam(entity.getNombre());
        command.addParam(entity.getModelo().toString());
        command.addParam(entity.getPrecio());
        command.addParam(entity.getFechaLanzamiento());
        try {
            databaseManager.executeUpdate(command);
        } catch (SQLException | IOException e) {
            log.error("Error al insertar el funko", e);
        }

        SqlCommand query = new SqlCommand("SELECT * FROM funkos WHERE cod = ?");
        query.addParam(entity.getCod());
        try {
            ResultSet resultSet = databaseManager.executeQuery(query);
            if (resultSet.next()) {
                return FunkoDB.fromResultSet(resultSet);
            }
        } catch (SQLException | IOException e) {
            log.error("Error al obtener el funko", e);
        }
        return null;
    }


    @Override
    public FunkoDB findById(Integer id) {
        if (cache.containsKey(id)) {
            return cache.get(id);
        }
        SqlCommand query = new SqlCommand("SELECT * FROM funkos WHERE id = ?");
        query.addParam(id);
        try {
            ResultSet resultSet = databaseManager.executeQuery(query);
            if (resultSet.next()) {
                FunkoDB funkoDB = FunkoDB.fromResultSet(resultSet);
                cache.put(id, funkoDB);
                return funkoDB;
            }
        } catch (SQLException | IOException e) {
            log.error("Error al obtener el funko", e);
        }
        return null;
    }

    @Override
    public List<Funko> findAll() {
        List<Funko> funkos = new ArrayList<>();

        SqlCommand query = new SqlCommand("SELECT * FROM funkos");

        try {
            ResultSet resultSet = databaseManager.executeQuery(query);
            while (resultSet.next()) {
                funkos.add(FunkoDB.fromResultSet(resultSet));
            }
        } catch (SQLException | IOException e) {
            log.error("Error al obtener los funkos", e);
        }

        return funkos;
    }

    @Override
    public FunkoDB update(FunkoDB entity) {
        entity.setUpdatedDate(LocalDateTime.now());
        SqlCommand command = new SqlCommand("UPDATE funkos SET nombre = ?, modelo = ?, precio = ?, fecha_lanzamiento = ?, updated_date = ? WHERE id = ?");
        command.addParam(entity.getNombre());
        command.addParam(entity.getModelo().toString());
        command.addParam(entity.getPrecio());
        command.addParam(entity.getFechaLanzamiento());
        command.addParam(entity.getUpdatedDate());
        command.addParam(entity.getId());
        try {
            databaseManager.executeUpdate(command);
            return entity;
        } catch (SQLException | IOException e) {
            log.error("Error al actualizar el funko", e);
        }
        return null;
    }

    @Override
    public void deleteById(Integer id) {
        SqlCommand command = new SqlCommand("DELETE FROM funkos WHERE id = ?");
        command.addParam(id);
        try {
            databaseManager.executeUpdate(command);
            cache.remove(id);
        } catch (SQLException | IOException e) {
            log.error("Error al eliminar el funko", e);
        }
    }

    @Override
    public void saveAll(List<Funko> funkos) {
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("INSERT INTO funkos (cod, nombre, modelo, precio, fecha_lanzamiento) VALUES ");
        for (int i = 0; i < funkos.size(); i++) {
            queryBuilder.append("(?, ?, ?, ?, ?)");
            if (i < funkos.size() - 1) {
                queryBuilder.append(", ");
            }
        }
        SqlCommand sqlCommand = new SqlCommand(queryBuilder.toString());
        funkos.forEach(funko -> {
            sqlCommand.addParam(funko.getCod());
            sqlCommand.addParam(funko.getNombre());
            sqlCommand.addParam(funko.getModelo().toString());
            sqlCommand.addParam(funko.getPrecio());
            sqlCommand.addParam(funko.getFechaLanzamiento());
        });

        try {
            log.info("Insertando " + funkos.size() + " funkos");
            databaseManager.executeUpdate(sqlCommand);
        } catch (SQLException | IOException e) {
            log.error("Error al insertar los funkos", e);
        }
    }
}
