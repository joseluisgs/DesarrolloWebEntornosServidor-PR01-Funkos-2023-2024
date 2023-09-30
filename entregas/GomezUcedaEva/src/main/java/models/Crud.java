package models;

import java.util.List;
import java.util.Optional;

public interface Crud<T> {
    void save(T t);

    void update(T t);

    List<T> findAll();

    void delete(T t);

    void deleteAll();

    void findByNombre(String nombre);
}
