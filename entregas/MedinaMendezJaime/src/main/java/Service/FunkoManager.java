package Service;

import Models.FunkoBD;
import repositories.Crud;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class FunkoManager implements Crud<FunkoBD, UUID> {
    private static FunkoManager instance;
    private final Connection connection;

    private FunkoManager(Connection connection) {
        this.connection = connection;
    }

    public static FunkoManager getInstance(Connection connection) {
        if (instance == null) {
            instance = new FunkoManager(connection);
        }
        return instance;
    }

    /*CREATE*/
    public void save(FunkoBD funko) {
        try {
            String sqlQuery = "INSERT INTO FUNKOS (ID, COD, NOMBRE, MODELO, PRECIO, FECHA_LANZAMIENTO, CREATED_AT, UPDATED_AT) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setObject(1, funko.getId());
            statement.setObject(2, funko.getCod());
            statement.setString(3, funko.getNombre());
            statement.setString(4, String.valueOf(funko.getModelo()));
            statement.setDouble(5, funko.getPrecio());
            statement.setDate(6, Date.valueOf(funko.getFechaLanzamiento()));
            statement.setDate(7, Date.valueOf(funko.getCreated_at()));
            statement.setDate(8, Date.valueOf(funko.getUpdated_at()));
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al insertar: " + e.getMessage());
        }
    }

    /*READ*/

    /*public List<FunkoBD> findAll() {
        List<FunkoBD> funkos = new ArrayList<>();
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM FUNKOS");
            while (resultSet.next()) {
                funkos.add(new FunkoBD(
                        resultSet.getInt("ID"),
                        resultSet.getInt("COD"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("MODELO"),
                        resultSet.getDouble("PRECIO"),
                        resultSet.getDate("FECHA_LANZAMIENTO"),
                        resultSet.getDate("CREATED_AT"),
                        resultSet.getDate("UPDATED_AT")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error en findAll: " + e.getMessage());
        }
        return funkos;
    }

    @Override
    public boolean deleteById(UUID uuid) {
        try {
            String sqlQuery = "DELETE FROM FUNKOS WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, uuid.toString());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Error en findByNombre: " + e.getMessage());
        }
        return false;
    }

    public Optional<FunkoBD> findByNombre(String nombre) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM FUNKOS WHERE NOMBRE = ?");
            while (resultSet.next()) {
                return new FunkoBD(
                        resultSet.getInt("ID"),
                        resultSet.getInt("COD"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("MODELO"),
                        resultSet.getDouble("PRECIO"),
                        resultSet.getDate("FECHA_LANZAMIENTO"),
                        resultSet.getDate("CREATED_AT"),
                        resultSet.getDate("UPDATED_AT")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en findByNombre: " + e.getMessage());
        }
        return null;
    }

    public Optional<FunkoBD> findById(UUID id) {
        try {
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM FUNKOS WHERE ID = ?");
            while (resultSet.next()) {
                return new FunkoBD(
                        resultSet.getInt("ID"),
                        resultSet.getInt("COD"),
                        resultSet.getString("NOMBRE"),
                        resultSet.getString("MODELO"),
                        resultSet.getDouble("PRECIO"),
                        resultSet.getDate("FECHA_LANZAMIENTO"),
                        resultSet.getDate("CREATED_AT"),
                        resultSet.getDate("UPDATED_AT")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error en findById: " + e.getMessage());
        }
        return null;
    }

    *//*UPDATE*//*

    public void update(FunkoBD funko) {
        try {
            String sqlQuery = "UPDATE FUNKO SET ID = ?, COD = ?, NOMBRE = ?, MODELO = ?, PRECIO = ?, FECHA_LANZAMIENTO = ?, CREATED_AT = ?, UPDATED_AT = ? WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);

            statement.setInt(1, FunkoBD.getId());
            statement.setInt(2, FunkoBD.getCod());
            statement.setString(3, FunkoBD.getNombre());
            statement.setString(4, FunkoBD.getModelo());
            statement.setDouble(5, FunkoBD.getPrecio());
            statement.setDate(6, FunkoBD.getFechaLanzamiento());
            statement.setDate(7, FunkoBD.getCreated_at());
            statement.setDate(8, FunkoBD.getUpdated_at());
            statement.setInt(9, funko.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al actualizar: " + e.getMessage());
        }
    }

    *//*DELETE*//*

    public void delete(FunkoBD funko) {
        try {
            String sqlQuery = "DELETE FROM FUNKO WHERE ID = ?";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.setString(1, FunkoBD.getId().toString());

            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }

    public void deleteAll() {
        try {
            String sqlQuery = "DELETE FROM WEATHER";
            PreparedStatement statement = connection.prepareStatement(sqlQuery);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error al eliminar: " + e.getMessage());
        }
    }*/


}















