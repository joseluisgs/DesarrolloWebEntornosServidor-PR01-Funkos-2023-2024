package repositories;
import exceptions.FunkoException;
import models.Funko;

import java.sql.SQLException;
import java.util.List;

// Cogemos la interfaz Crud la contextualizamos a nuestro tipo y añadimos metodos sin falta
// Por ejeplo un FibByNombre
public interface FunkoRepository extends CrudRepository<Funko, Long, FunkoException> {
    // Buscar por nombre
    List<Funko> findByNombre(String nombre) throws SQLException;
}
