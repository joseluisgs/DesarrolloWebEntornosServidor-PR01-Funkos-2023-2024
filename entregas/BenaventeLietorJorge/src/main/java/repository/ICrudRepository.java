package repository;

public interface ICrudRepository<T, PK> {
    T save(T entity);

    T findById(PK id);

    T update(T entity);

    void deleteById(PK id);
}
