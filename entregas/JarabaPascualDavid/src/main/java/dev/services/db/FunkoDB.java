package dev.services.db;

import dev.models.Funko;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface FunkoDB{

    List<Funko> findAll() throws SQLException, IOException;

    Optional<Funko> findById(UUID id) throws SQLException, IOException;

    Funko save(Funko entity) throws SQLException, IOException;

    Funko update(UUID id, Funko entity) throws SQLException, IOException;

    boolean delete(UUID id) throws SQLException, IOException;

    Optional<Funko> findByName(String name) throws SQLException, IOException;

    boolean backup(String path) throws IOException, SQLException;

}
