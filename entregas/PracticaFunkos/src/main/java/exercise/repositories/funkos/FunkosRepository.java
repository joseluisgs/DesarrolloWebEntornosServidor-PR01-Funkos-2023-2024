package exercise.repositories.funkos;

import exercise.models.Funko;
import exercise.repositories.crud.CRUDRepository;

import javax.lang.model.element.Name;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public interface FunkosRepository extends CRUDRepository<Funko, Integer, Name> {

    static void deleteAll() throws SQLException;

    void backup() throws SQLException, IOException;

    void restore() throws SQLException, IOException;

    List<Funko> findByNombre(String nombre) throws SQLException;

}
