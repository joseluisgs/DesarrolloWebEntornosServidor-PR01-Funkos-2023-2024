package dev.repositories;

import dev.managers.DatabaseManager;
import dev.models.Funko;
import dev.models.Modelo;
import dev.models.SqlCommand;
import java.io.IOException;
import java.security.KeyStore;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FunkosRepoImpl implements FunkosRepo{

    private final DatabaseManager databaseManager;

    public FunkosRepoImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    LinkedList<Map.Entry<UUID, Funko>> funkos = new LinkedList<>();

    @Override
    public List<Funko> findAll() throws SQLException, IOException {

        String sql = "SELECT * FROM funkos";
        databaseManager.open();

        ResultSet resultSet = databaseManager.executeQuery(new SqlCommand(sql));

        System.out.println(resultSet);

        List<Funko> funkos = new ArrayList<>();

        while (resultSet.next()) {

            Funko f = new Funko(
                    UUID.fromString(resultSet.getString("cod")),
                    resultSet.getString("nombre"),
                    Modelo.valueOf(resultSet.getString("modelo")),
                    resultSet.getDouble("precio"),
                    resultSet.getDate("fecha_lanzamiento").toLocalDate()
            );

            funkos.add(f);

        }

        return funkos;

    }

    @Override
    public Optional<Funko> findById(UUID uuid) throws SQLException, IOException {

        String sql = "SELECT * FROM funkos WHERE cod = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(uuid);

        ResultSet resultSet = databaseManager.executeQuery(sqlCommand);

        if (resultSet.next()) {

            Funko f = new Funko(
                    UUID.fromString(resultSet.getString("cod")),
                    resultSet.getString("nombre"),
                    Modelo.valueOf(resultSet.getString("modelo")),
                    resultSet.getDouble("precio"),
                    resultSet.getDate("fecha_lanzamiento").toLocalDate()
            );

            return Optional.of(f);

        }

        return Optional.empty();

    }

    @Override
    public Funko save(Funko entity) throws SQLException, IOException {

        String sql = "INSERT INTO funkos (cod, nombre, modelo, precio, fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(entity.getCodigo());
        sqlCommand.addParam(entity.getNombre());
        sqlCommand.addParam(entity.getModelo().toString());
        sqlCommand.addParam(entity.getPrecio());
        sqlCommand.addParam(entity.getFechaLanzamiento());

        databaseManager.executeUpdate(sqlCommand);

        databaseManager.close();

        return entity;

    }

    @Override
    public Funko update(UUID id, Funko entity) throws SQLException, IOException {
        String sql = "UPDATE funkos SET nombre = ?, modelo = ?, precio = ?, fecha_lanzamiento = ? WHERE cod = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(id);

        databaseManager.executeUpdate(sqlCommand);

        return entity;

    }

    @Override
    public void delete(UUID id) throws SQLException, IOException {

        String sql = "DELETE FROM funkos WHERE cod = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(id);

        databaseManager.executeUpdate(sqlCommand);

    }

    @Override
    public Optional<Funko> findByName(String name) throws SQLException, IOException {

        String sql = "SELECT * FROM funkos WHERE nombre = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(name);

        ResultSet resultSet = databaseManager.executeQuery(sqlCommand);

        if (resultSet.next()) {

            Funko f = new Funko(
                    UUID.fromString(resultSet.getString("cod")),
                    resultSet.getString("nombre"),
                    Modelo.valueOf(resultSet.getString("modelo")),
                    resultSet.getDouble("precio"),
                    resultSet.getDate("fecha_lanzamiento").toLocalDate()
            );

            return Optional.of(f);

        }

        return Optional.empty();

    }


}
