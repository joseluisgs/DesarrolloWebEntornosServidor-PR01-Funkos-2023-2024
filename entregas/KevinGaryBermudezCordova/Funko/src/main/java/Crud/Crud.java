package Crud;

import models.Funko;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface Crud<T, id> {
    CompletableFuture<Funko> save(T t) throws SQLException;

    // Actualizar
    CompletableFuture<Funko> update(T t) throws SQLException;

    // Buscar por ID
    CompletableFuture<Optional<Funko>> findById(id id) throws SQLException;

    // Buscar todos
    CompletableFuture<List<Funko>> findAll() throws SQLException;
    CompletableFuture<Void> deleteAll() throws SQLException;


}
