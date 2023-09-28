package com.madirex.repositories;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Interfaz que define las operaciones CRUD sobre un repositorio
 *
 * @param <T>  Tipo de la entidad
 * @param <I> Tipo del ID de la entidad
 */
public interface CRUDRepository<T, I> {
    /**
     * Devuelve todos los elementos del repositorio
     *
     * @return Lista de elementos
     */
    List<T> findAll() throws SQLException;

    /**
     * Devuelve un elemento del repositorio
     *
     * @param id Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    Optional<T> findById(I id) throws SQLException;

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    Optional<T> save(T entity) throws SQLException;

    /**
     * Actualiza un elemento del repositorio
     *
     * @param id     Id del elemento a actualizar
     * @param entity Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    Optional<T> update(I id, T entity) throws SQLException;

    /**
     * Borra un elemento del repositorio
     *
     * @param id Id del elemento a borrar
     * @return ¿Borrado?
     */
    boolean delete(I id) throws SQLException;
}