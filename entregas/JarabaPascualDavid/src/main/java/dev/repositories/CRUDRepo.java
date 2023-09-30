package dev.repositories;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDRepo<T, ID> {

    List<T> findAll() throws SQLException, IOException;

    Optional<T> findById(ID id) throws SQLException, IOException;

    T save(T entity) throws SQLException, IOException;

    T update(ID id, T entity) throws SQLException, IOException;

    void delete(ID id) throws SQLException, IOException;
}