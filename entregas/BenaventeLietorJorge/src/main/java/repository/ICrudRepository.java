package repository;

import java.util.List;

public interface ICrudRepository<TDB, T, PK> {
    T save(T entity);

    T findById(PK id);
    List<T> findAll();

    T update(TDB entity);

    void deleteById(PK id);
}
