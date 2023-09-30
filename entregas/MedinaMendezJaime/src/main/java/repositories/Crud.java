package repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Crud<T, ID> {
    void save(T t) throws SQLException;

    // Actualizar
    /*void update(T t) throws SQLException;

    // Buscar por ID
    Optional<T> findById(ID id) throws SQLException;

    // Buscar por nombre
    Optional<T> findByNombre(String name) throws SQLException;

    // Buscar todos
    List<T> findAll() throws SQLException;

    // Borrar por ID
    boolean deleteById(ID id) throws SQLException;

    // Borrar todos
    void deleteAll() throws SQLException;*/
}
