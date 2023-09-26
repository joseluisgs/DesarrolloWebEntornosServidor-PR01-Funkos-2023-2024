package repository;

import database.models.Funko;
import database.models.FunkoDB;

import java.util.List;

public interface IFunkoRepository extends ICrudRepository<FunkoDB, Funko, Integer> {
    void saveAll(List<Funko> funkos);
}
