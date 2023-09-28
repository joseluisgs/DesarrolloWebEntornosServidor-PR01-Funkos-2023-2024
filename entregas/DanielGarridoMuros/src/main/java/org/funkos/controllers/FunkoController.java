package org.funkos.controllers;

import org.funkos.models.Funko;
import org.funkos.repositories.funko.FunkoRepository;
import org.funkos.repositories.funko.FunkoRepositoryImpl;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FunkoController {

    private FunkoRepository funkoRepository;

    public FunkoController(FunkoRepository funkoRepository){
        this.funkoRepository = funkoRepository;
    }

    public Funko insertFunko(Funko funko){
        Funko inserted = null;
        try {
            inserted = funkoRepository.save(funko);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return inserted;
    }

    public Funko updateFunko(Funko funko){
        Funko updated = null;
        try {
            updated = funkoRepository.update(funko);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return updated;
    }

    public Funko findById(Integer id){
        try {
            return funkoRepository.findById(id).orElse(null);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteById(Integer id){
        try {
            return funkoRepository.deleteById(id);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteAll(){
        try {
            funkoRepository.deleteAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Funko> findAll(){
        try {
            return funkoRepository.findAll();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void backup() {
        try {
            funkoRepository.backup();
        } catch (IOException | SQLException e) {
            System.err.println("Error al hacer backup: " + e.getMessage());
        }
    }

    public void restore() {
        try {
            funkoRepository.restore();
        } catch (IOException | SQLException e) {
            System.err.println("Error al hacer restore: " + e.getMessage());
        }
    }


}
