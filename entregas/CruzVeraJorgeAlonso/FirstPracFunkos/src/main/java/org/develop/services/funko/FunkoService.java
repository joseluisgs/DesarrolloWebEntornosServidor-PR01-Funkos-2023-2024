package org.develop.services.funko;

import org.develop.excepciones.FunkoNotFoundException;
import org.develop.excepciones.FunkoNotSaveException;
import org.develop.model.Funko;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FunkoService {
    Funko save(Funko funko) throws SQLException, FunkoNotSaveException;

    Funko update(Funko funko) throws SQLException, FunkoNotFoundException;

    Optional<Funko> findById(Integer id) throws SQLException;

    List<Funko> findAll() throws SQLException;

    List<Funko> findByName(String name) throws SQLException;

    boolean deleteById(Integer id) throws SQLException;

    void deleteAll() throws SQLException;
    boolean backup(String name) throws SQLException;

}
