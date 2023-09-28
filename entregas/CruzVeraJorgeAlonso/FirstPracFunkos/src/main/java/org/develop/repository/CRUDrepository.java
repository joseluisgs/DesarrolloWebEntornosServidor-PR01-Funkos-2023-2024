package org.develop.repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CRUDrepository<T,ID>{

    // Guardar
    T save(T t) throws SQLException;

    // Actualizar
    T update(T t) throws SQLException;

    // Buscar por ID
    Optional<T> findById(ID id) throws SQLException;

    // Buscar todos
    List<T> findAll() throws SQLException;

    // Borrar por ID
    boolean deleteById(ID id) throws SQLException;

    // Borrar todos
    void deleteAll() throws SQLException;

    // Hacer Backup
    boolean backup(String file) throws SQLException;
}
