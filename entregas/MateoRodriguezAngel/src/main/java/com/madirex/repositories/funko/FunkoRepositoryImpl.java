package com.madirex.repositories.funko;

import com.madirex.models.Funko;
import com.madirex.models.Model;
import com.madirex.services.database.DatabaseManager;
import lombok.RequiredArgsConstructor;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementación de la interfaz FunkoRepository
 */
@RequiredArgsConstructor
public class FunkoRepositoryImpl implements FunkoRepository {
    private final DatabaseManager database;

    /**
     * Devuelve todos los elementos del repositorio
     *
     * @return Optional de la lista de elementos
     */
    @Override
    public List<Funko> findAll() throws SQLException {
        List<Funko> list = new ArrayList<>();
        var sql = "SELECT * FROM funko";
        var res = database.select(sql).orElseThrow();
        while (res.next()) {
            list.add(Funko.builder()
                    .cod(UUID.fromString(res.getString("cod")))
                    .name(res.getString("nombre"))
                    .model(Model.valueOf(res.getString("modelo")))
                    .price(res.getDouble("precio"))
                    .releaseDate(res.getDate("fecha_lanzamiento").toLocalDate())
                    .build());
        }
        database.close();
        return list;
    }

    /**
     * Busca un elemento en el repositorio por su id
     *
     * @param id Id del elemento a buscar
     * @return Optional del elemento encontrado
     */
    @Override
    public Optional<Funko> findById(String id) throws SQLException {
        Optional<Funko> optReturn = Optional.empty();
        var sql = "SELECT * FROM funko WHERE cod = ?";
        var res = database.select(sql, id).orElseThrow();
        if (res.next()) {
            optReturn = Optional.of(Funko.builder()
                    .cod(UUID.fromString(res.getString("cod")))
                    .name(res.getString("nombre"))
                    .model(Model.valueOf(res.getString("modelo")))
                    .price(res.getDouble("precio"))
                    .releaseDate(res.getDate("fecha_lanzamiento").toLocalDate())
                    .build());
        }
        database.close();
        return optReturn;
    }

    /**
     * Guarda un elemento en el repositorio
     *
     * @param entity Elemento a guardar
     * @return Optional del elemento guardado
     */
    @Override
    public Optional<Funko> save(Funko entity) throws SQLException {
        var sql = "INSERT INTO funko (cod, nombre, modelo, precio, fecha_lanzamiento, created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        database.open();
        database.insertAndGetKey(sql, entity.getCod().toString(),
                        entity.getName(),
                        entity.getModel().toString(),
                        entity.getPrice(),
                        entity.getReleaseDate(),
                        LocalDateTime.now(),
                        LocalDateTime.now())
                .orElseThrow();
        database.close();
        return Optional.of(entity);
    }

    /**
     * Borra un elemento del repositorio
     *
     * @param id Id del elemento a borrar
     * @return ¿Borrado?
     */
    @Override
    public boolean delete(String id) throws SQLException {
        var sql = "DELETE FROM funko WHERE cod= ?";
        database.open();
        var rs = database.delete(sql, id);
        database.close();
        return (rs == 1);
    }

    /**
     * [Método deshabilitado]
     *
     * @param id Id del elemento a actualizar
     * @param entity  Elemento con los nuevos datos
     * @return Optional del elemento actualizado
     */
    @Override
    public Optional<Funko> update(String id, Funko entity) throws SQLException {
        var sql = "UPDATE funko SET nombre = ?, modelo = ?, precio = ?, fecha_lanzamiento = ?, " +
                "updated_at = ? WHERE cod = ?";
        database.open();
        database.update(sql,
                        entity.getName(),
                        entity.getModel().toString(),
                        entity.getPrice(),
                        entity.getReleaseDate(),
                        LocalDateTime.now(),
                        id);
        database.close();
        return Optional.of(entity);
    }


    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Lista de elementos encontrados
     */
    @Override
    public List<Funko> findByName(String name) throws SQLException {
        return findAll().stream()
                .filter(p -> p.getName().toLowerCase().contains(name.toLowerCase()))
                .toList();
    }
}

