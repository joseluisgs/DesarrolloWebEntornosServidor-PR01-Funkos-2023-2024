package CRUD;
import model.Funko;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FunkoRepositoryImpl implements crudfunko<Funko, UUID> {

    private Connection connection;

    public FunkoRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Funko save(Funko funko) throws SQLException {
        String sql = "INSERT INTO funkos (cod, nombre, modelo, precio, fecha_lanzamiento) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setObject(1, funko.getCod());
            preparedStatement.setString(2, funko.getNombre());
            preparedStatement.setString(3, funko.getModelo());
            preparedStatement.setDouble(4, funko.getPrecio());
            preparedStatement.setDate(5, java.sql.Date.valueOf(funko.getFechaLanzamiento()));
            preparedStatement.executeUpdate();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (generatedKeys.next()) {
                funko.setId(generatedKeys.getInt(1));
            }
        }
        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException {
        String sql = "UPDATE funkos SET nombre = ?, modelo = ?, precio = ?, fecha_lanzamiento = ? WHERE id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, funko.getNombre());
            preparedStatement.setString(2, funko.getModelo());
            preparedStatement.setDouble(3, funko.getPrecio());
            preparedStatement.setDate(4, java.sql.Date.valueOf(funko.getFechaLanzamiento()));
            preparedStatement.setInt(5, funko.getId());
            preparedStatement.executeUpdate();
        }
        return funko;
    }

    @Override
    public Optional<Funko> findById(UUID id) throws SQLException {
        String sql = "SELECT * FROM funkos WHERE cod = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                Funko funko = extractFunkoFromResultSet(resultSet);
                return Optional.of(funko);
            }
        }
        return Optional.empty();
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        List<Funko> funkos = new ArrayList<>();
        String sql = "SELECT * FROM funkos";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            while (resultSet.next()) {
                Funko funko = extractFunkoFromResultSet(resultSet);
                funkos.add(funko);
            }
        }
        return funkos;
    }

    @Override
    public boolean deleteById(UUID id) throws SQLException {
        String sql = "DELETE FROM funkos WHERE cod = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setObject(1, id);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    @Override
    public void deleteAll() throws SQLException {
        String sql = "DELETE FROM funkos";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql);
        }
    }

    private Funko extractFunkoFromResultSet(ResultSet resultSet) throws SQLException {
        Funko funko = new Funko();
        funko.setId(resultSet.getInt("id"));
        funko.setCod((UUID) resultSet.getObject("cod"));
        funko.setNombre(resultSet.getString("nombre"));
        funko.setModelo(resultSet.getString("modelo"));
        funko.setPrecio(resultSet.getDouble("precio"));
        funko.setFechaLanzamiento(resultSet.getDate("fecha_lanzamiento").toLocalDate());
        return funko;
    }
}
