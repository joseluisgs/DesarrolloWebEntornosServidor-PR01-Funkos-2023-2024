package org.repositories;

import org.models.Funko;

import java.sql.SQLException;
import java.util.List;

public interface FunkoRepository extends CrudRepository< Funko, Long> {
    List<Funko> findByNombre(String nombre) throws SQLException;
}
