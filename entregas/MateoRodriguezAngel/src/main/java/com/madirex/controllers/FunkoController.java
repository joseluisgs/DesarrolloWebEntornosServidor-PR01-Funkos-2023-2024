package com.madirex.controllers;

import com.madirex.exceptions.FunkoException;
import com.madirex.exceptions.FunkoNotFoundException;
import com.madirex.exceptions.FunkoNotValidException;
import com.madirex.models.Funko;
import com.madirex.repositories.funko.FunkoRepository;
import com.madirex.services.crud.funko.FunkoService;
import com.madirex.services.database.DatabaseManager;
import com.madirex.validators.FunkoValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FunkoController implements BaseController<Funko>{
    private final Logger logger = LoggerFactory.getLogger(DatabaseManager.class);

    private final FunkoService funkoService;

    public FunkoController(FunkoService funkoService) {
        this.funkoService = funkoService;
    }

    public List<Funko> findAll() throws SQLException {
        logger.debug("FindAll");
        return funkoService.findAll();
    }

    public Optional<Funko> findById(String id) throws SQLException, FunkoNotFoundException {
        String msg = "FindById " + id;
        logger.debug(msg);
        return funkoService.findById(id);
    }

    public List<Funko> findByName(String name) throws SQLException {
        String msg = "FindByName " + name;
        logger.debug(msg);
        return funkoService.findByName(name);
    }

    public Optional<Funko> save(Funko funko) throws SQLException, FunkoNotValidException {
        String msg = "Save " + funko;
        logger.debug(msg);
        FunkoValidator.validate(funko);
        try {
            return funkoService.save(funko);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Funko> update(String id, Funko funko) throws SQLException, FunkoNotValidException,
            FunkoNotFoundException {
        String msg = "Update " + funko;
        logger.debug(msg);
        FunkoValidator.validate(funko);
        try {
            return funkoService.update(id, funko);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
    }

    public Optional<Funko> delete(String id) throws SQLException, FunkoNotFoundException {
        String msg = "Delete " + id;
        logger.debug(msg);
        var funko = funkoService.findById(id).orElseThrow(() ->
                new FunkoNotFoundException("No se ha encontrado el funko con id " + id));
        try {
            funkoService.delete(id);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
        return Optional.of(funko);
    }

    public void backup(String url, String fileName) {
        try {
            funkoService.backup(url, fileName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}