package dev.repositories;

import dev.models.Funko;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

public interface FunkosRepo extends CRUDRepo<Funko, UUID>{

    Optional<Funko> findByName(String name) throws SQLException, IOException;

}
