package repository;

import java.util.List;

public interface ICrudRepository<TDB, T, PK, EX extends Throwable> {
    T save(T entity) throws EX;

    T findById(PK id) throws EX;
    List<T> findAll();

    T update(TDB entity) throws EX;

    void deleteById(PK id) throws EX;
}
