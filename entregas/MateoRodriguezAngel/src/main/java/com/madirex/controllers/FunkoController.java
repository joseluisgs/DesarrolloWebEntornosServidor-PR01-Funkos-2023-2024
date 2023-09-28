package com.madirex.controllers;

import com.madirex.exceptions.FunkoNotFoundException;
import com.madirex.exceptions.FunkoNotValidException;
import com.madirex.models.Funko;
import com.madirex.repositories.funko.FunkoRepository;
import com.madirex.services.database.DatabaseManager;
import com.madirex.validators.FunkoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FunkoController implements BaseController<Funko>{
    private final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private final FunkoRepository funkoRepository;

    public FunkoController(FunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
    }

    public List<Funko> findAll() throws SQLException {
        logger.debug("FindAll");
        return funkoRepository.findAll();
    }

    public Funko findById(String id) throws SQLException, FunkoNotFoundException {
        String msg = "FindById " + id;
        logger.debug(msg);
        return funkoRepository.findById(id)
                .orElseThrow(() -> new FunkoNotFoundException("No se ha encontrado el funko con id " + id));
    }

    public List<Funko> findByName(String name) throws SQLException {
        String msg = "FindByName " + name;
        logger.debug(msg);
        return funkoRepository.findByName(name);
    }

    public Optional<Funko> save(Funko funko) throws SQLException, FunkoNotValidException {
        String msg = "Save " + funko;
        logger.debug(msg);
        FunkoValidator.validate(funko);
        return funkoRepository.save(funko);
    }

    public Optional<Funko> update(String id, Funko funko) throws SQLException, FunkoNotValidException, FunkoNotFoundException {
        String msg = "Update " + funko;
        logger.debug(msg);
        funkoRepository.findById(funko.getCod().toString()).orElseThrow(() ->
                new FunkoNotFoundException("No se ha encontrado el funko con id " + funko.getCod().toString()));
        FunkoValidator.validate(funko);
        return funkoRepository.update(id, funko);
    }

    public Optional<Funko> delete(String id) throws SQLException, FunkoNotFoundException {
        String msg = "Delete " + id;
        logger.debug(msg);
        var funko = funkoRepository.findById(id).orElseThrow(() ->
                new FunkoNotFoundException("No se ha encontrado el funko con id " + id));
        funkoRepository.delete(id);
        return Optional.of(funko);
    }
}