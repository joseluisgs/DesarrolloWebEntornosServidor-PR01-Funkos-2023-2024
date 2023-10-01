package dev.repositories;

import dev.exceptions.FunkoNoEncontrado;
import dev.managers.DatabaseManager;
import dev.models.Funko;
import dev.models.Modelo;
import dev.models.SqlCommand;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FunkosRepoImpl implements FunkosRepo, AutoCloseable{

    private final DatabaseManager databaseManager;

    public FunkosRepoImpl(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
    }

    @Override
    public List<Funko> findAll() throws SQLException, IOException {

        String sql = "SELECT * FROM funkos";
        databaseManager.open();

        try (ResultSet resultSet = databaseManager.executeQuery(new SqlCommand(sql))) {

            List<Funko> funkos = new ArrayList<>();

            while (resultSet.next()) {

                Funko f = Funko.builder().codigo(UUID.fromString(resultSet.getString("cod")))
                        .nombre(resultSet.getString("nombre"))
                        .modelo(Modelo.valueOf(resultSet.getString("modelo")))
                        .precio(resultSet.getDouble("precio"))
                        .fechaLanzamiento(resultSet.getDate("fecha_lanzamiento").toLocalDate())
                        .build();

                funkos.add(f);

            }

            return funkos;

        }

    }

    @Override
    public Optional<Funko> findById(UUID uuid) throws SQLException, IOException {

        String sql = "SELECT * FROM funkos WHERE cod = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(uuid);

        try (ResultSet resultSet = databaseManager.executeQuery(sqlCommand)) {

            if (resultSet.next()) {

                Funko f = Funko.builder().codigo(UUID.fromString(resultSet.getString("cod")))
                        .nombre(resultSet.getString("nombre"))
                        .modelo(Modelo.valueOf(resultSet.getString("modelo")))
                        .precio(resultSet.getDouble("precio"))
                        .fechaLanzamiento(resultSet.getDate("fecha_lanzamiento").toLocalDate())
                        .build();

                return Optional.of(f);

            }

        }

        throw new FunkoNoEncontrado("No se ha encontrado el funko con el id " + uuid.toString());

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

        databaseManager.close();

        return entity;

    }

    @Override
    public boolean delete(UUID id) throws SQLException, IOException {

        String sql = "DELETE FROM funkos WHERE cod = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(id);

        int res = databaseManager.executeUpdate(sqlCommand);

        return res > 0;

    }

    @Override
    public Optional<Funko> findByName(String name) throws SQLException, IOException {

        String sql = "SELECT * FROM funkos WHERE nombre = ?";

        SqlCommand sqlCommand = new SqlCommand(sql);
        sqlCommand.addParam(name);

        try (ResultSet resultSet = databaseManager.executeQuery(sqlCommand)) {

            if (resultSet.next()) {

                Funko f = Funko.builder().codigo(UUID.fromString(resultSet.getString("cod")))
                        .nombre(resultSet.getString("nombre"))
                        .modelo(Modelo.valueOf(resultSet.getString("modelo")))
                        .precio(resultSet.getDouble("precio"))
                        .fechaLanzamiento(resultSet.getDate("fecha_lanzamiento").toLocalDate())
                        .build();

                return Optional.of(f);

            }

        }

        throw new FunkoNoEncontrado("No se ha encontrado el funko con el nombre " + name);
    }


    @Override
    public void close() throws Exception {
        databaseManager.close();
    }
}
