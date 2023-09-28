package com.madirex.controllers;

import com.madirex.exceptions.FunkoNotFoundException;
import com.madirex.exceptions.FunkoNotValidException;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Controlador base
 * @param <T> Entity
 */
public interface BaseController<T> {
    List<T> findAll() throws SQLException;

    Optional<T> findById(String id) throws SQLException, FunkoNotFoundException;

    List<T> findByName(String name) throws SQLException;

    Optional<T> save(T entity) throws SQLException, FunkoNotValidException;

    Optional<T> update(String id, T entity) throws SQLException, FunkoNotValidException, FunkoNotFoundException;

    Optional<T> delete(String id) throws SQLException, FunkoNotFoundException;
}
