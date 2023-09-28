package services;

import models.Crud;
import models.Funko;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class FunkoManager implements Crud<Funko> {
    private final Connection connection;

    private FunkoManager(Connection connection) {
        this.connection = connection;
    }

    public void save(Funko funko) {
        try {
            String sqlQuery = "INSERT INTO FUNKOS (ID, cod, nombre, modelo, precio, fechaLanzamiento, createdAt, updatedAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(funko.getId()));
            statement.setString(2, String.valueOf(funko.getCod()));
            statement.setDouble(3, Double.parseDouble(funko.getNombre()));
            statement.setTimestamp(4, Timestamp.valueOf(String.valueOf(funko.getModelo())));
            statement.setDouble(5, funko.getPrecio());
            statement.setTimestamp(6, Timestamp.valueOf(funko.getFecha_lanzamiento().atStartOfDay()));
            statement.setString(7, String.valueOf(funko.getCreated_at()));
            statement.setTimestamp(8, Timestamp.valueOf(funko.getUpdated_at().atStartOfDay()));

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    @Override
    public void update(Funko funko) {
        try {
            String sqlQuery = "UPDATE FUNKOS SET ID = ?, updatedAt = ?, createdAt = ?, modelo = ?, precio = ?, fechaLanzamiento = ? WHERE nombre = ? AND cod = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            statement.setString(1, String.valueOf(funko.getId()));
            statement.setTimestamp(2, Timestamp.valueOf(funko.getUpdated_at().atStartOfDay()));
            statement.setString(3, String.valueOf(funko.getCreated_at()));
            statement.setTimestamp(4, Timestamp.valueOf(String.valueOf(funko.getModelo())));
            statement.setDouble(5, funko.getPrecio());
            statement.setTimestamp(6, Timestamp.valueOf(funko.getFecha_lanzamiento().atStartOfDay()));
            statement.setString(7, String.valueOf(funko.getNombre()));
            statement.setString(8, String.valueOf(funko.getCod()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    @Override
    public List<Funko> findAll() {
        List<Funko> funko = new ArrayList<>();
        try {
            String sqlQuery = "SELECT * FROM FUNKOS";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return funko;
    }


    @Override
    public void delete(Funko funko) {
        try {
            String sqlQuery = "DELETE FROM FUNKOS WHERE nombre = ? AND cod = ? and ID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, String.valueOf(funko.getNombre()));
            statement.setString(2, String.valueOf(funko.getCod()));
            statement.setString(3, String.valueOf(funko.getId()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    @Override
    public void deleteAll() {
        try {
            String sqlQuery = "DELETE FROM FUNKOS";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    @Override
    public void findByNombre(String nombre) {
        try {
            String sqlQuery = "SELECT * FROM FUNKOS WHERE nombre = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, nombre);
            statement.executeQuery();
        } catch (SQLException e) {
            System.out.println("Error al buscar: " + e.getMessage());
        }
    }
}

