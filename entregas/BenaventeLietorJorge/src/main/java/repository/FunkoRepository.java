package repository;

import database.manager.DatabaseManager;
import database.models.Funko;
import database.models.FunkoDB;
import database.models.SqlCommand;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Log4j2
public class FunkoRepository implements IFunkoRepository {
    private final DatabaseManager databaseManager;
    private static FunkoRepository instance;

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
        return null;
    }

    @Override
    public FunkoDB findById(Integer id) {
        return null;
    }

    @Override
    public FunkoDB update(Funko entity) {
        return null;
    }

    @Override
    public void deleteById(Integer id) {

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
