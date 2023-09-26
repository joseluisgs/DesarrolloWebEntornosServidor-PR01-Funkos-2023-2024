package repository;

import database.models.Funko;
import database.models.FunkoDB;
import exceptions.FunkoException;

import java.util.List;

public interface IFunkoRepository extends ICrudRepository<FunkoDB, Funko, Integer, FunkoException> {
    void saveAll(List<Funko> funkos);
}
