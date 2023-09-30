package org.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID> {

    T save(T t) throws SQLException;


    T update(T t) throws SQLException;


    Optional<T> findById(ID id) throws SQLException;


    List<T> findAll() throws SQLException;


    boolean deleteById(ID id) throws SQLException;


    void deleteAll() throws SQLException;
}