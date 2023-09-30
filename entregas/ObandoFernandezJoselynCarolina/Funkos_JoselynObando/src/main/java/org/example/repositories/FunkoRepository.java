package org.example.repositories;

import org.example.models.Funko;

import java.sql.SQLException;
import java.util.List;

public  interface FunkoRepository extends CrudRepository <Funko, Integer> {
    List<Funko> findByNombre(String nombre) throws SQLException;
}
