package exercise.services.funkos;

import exercise.exceptions.funkos.FunkoException;
import exercise.models.Funko;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

// CRUD de Alumnos
public interface FunkosService {
    List<Funko> findAll() throws SQLException;

    List<Funko> findAllByNombre(String nombre) throws SQLException;

    Optional<Funko> findById(long id) throws SQLException;

    Funko save(Funko funko) throws SQLException, FunkoException;

    Funko update(Funko funko) throws SQLException, FunkoException;

    boolean deleteById(long id) throws SQLException;

    void deleteAll() throws SQLException;
}
