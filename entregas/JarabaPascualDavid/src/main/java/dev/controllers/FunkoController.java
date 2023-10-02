package dev.controllers;


import dev.models.Funko;
import dev.models.Modelo;
import dev.services.FunkoService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FunkoController {

    private final FunkoService service;


    public FunkoController(FunkoService service) {
        this.service = service;
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

    public List<Funko> funkosLanzadosEnAnoEspecifico(int ano) throws SQLException, IOException {
        return service.funkosLanzadosEnAnoEspecifico(ano);
    }

    public List<Funko> funkosContienenPalabra(String palabra) throws SQLException, IOException {
        return service.funkosContienenPalabra(palabra);
    }

    public int numFunkosDe(String palabra) throws SQLException, IOException {
        return service.funkosContienenPalabra(palabra).size();
    }

    public boolean backup(String path) throws SQLException, IOException {
        return service.backup(path);
    }

}
