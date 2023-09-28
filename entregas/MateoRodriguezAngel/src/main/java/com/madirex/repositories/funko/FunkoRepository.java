package com.madirex.repositories.funko;

import com.madirex.models.Funko;
import com.madirex.repositories.CRUDRepository;

import java.sql.SQLException;
import java.util.List;

/**
 * Interfaz que define las operaciones CRUD de FunkoRepository
 */
public interface FunkoRepository extends CRUDRepository<Funko, String> {
    /**
     * Busca un elemento en el repositorio por su nombre
     *
     * @param name Nombre del elemento a buscar
     * @return Lista de elementos encontrados
     */
    List<Funko> findByName(String name) throws SQLException;
}
