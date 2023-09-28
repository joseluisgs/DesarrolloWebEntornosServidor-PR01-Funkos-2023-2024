package org.funkos.repositories.base;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, ID, EX extends Exception> {

    T save(T t) throws SQLException, EX;

    T update(T t) throws SQLException, EX;

    Optional<T> findById(ID id) throws SQLException;

    List<T> findAll() throws SQLException;

    boolean deleteById(ID id) throws SQLException;

    void deleteAll() throws SQLException;

}
