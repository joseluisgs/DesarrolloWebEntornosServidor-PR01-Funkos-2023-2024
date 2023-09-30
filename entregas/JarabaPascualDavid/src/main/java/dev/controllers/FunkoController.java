package dev.controllers;

import dev.managers.DatabaseManager;
import dev.models.Funko;
import dev.models.Modelo;
import dev.services.FunkoService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FunkoController {

    private final FunkoService service;


    public FunkoController() throws FileNotFoundException {
        this.service = new FunkoService(DatabaseManager.getInstance());
    }

    public void importCSV() throws IOException, SQLException {
        service.importCSV();
    }

    public Optional<Funko> funkoMasCaro() throws SQLException, IOException {
        return service.funkoMasCaro();
    }


    public double mediaPrecioFunkos() throws SQLException, IOException {
        return service.mediaPrecioFunkos();
    }


    public Map<Modelo, List<Funko>> funkosPorModelo() throws SQLException, IOException {
        return service.funkosPorModelo();
    }

    public Map<Modelo, Integer> numFunkosPorModelo() throws SQLException, IOException {
        return service.numFunkosPorModelo();
    }

    public void exportJSON() throws SQLException, IOException {
        service.exportJSON();
    }

}
