package org.develop.repository;

import org.develop.model.Funko;

import java.sql.SQLException;
import java.util.List;

public interface FunkoRepository extends CRUDrepository<Funko,Integer>{
    List<Funko> findByName(String name) throws SQLException;


}
