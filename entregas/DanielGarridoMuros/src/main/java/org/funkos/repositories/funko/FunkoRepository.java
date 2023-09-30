package org.funkos.repositories.funko;

import org.funkos.models.Funko;
import org.funkos.repositories.base.CrudRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface FunkoRepository extends CrudRepository<Funko, Integer, SQLException> {

    Optional<Funko> findByNombre(String nombre) throws SQLException;

    void backup() throws  SQLException, IOException;

    void restore() throws SQLException, IOException;
}
