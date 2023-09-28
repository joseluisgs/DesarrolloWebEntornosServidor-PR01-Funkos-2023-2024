package dev;

import dev.controllers.FunkoController;
import dev.managers.DatabaseManager;
import dev.services.FunkoService;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {
        System.out.println("Hello world!");

        FunkoController funk = new FunkoController();
        funk.importCSV();

//        funk.exportJSON();

        System.out.println("Funko mas caro");
        System.out.println(funk.funkoMasCaro());

        System.out.println("Media de precio de funkos");
        System.out.println(funk.mediaPrecioFunkos());

        System.out.println("Funkos por modelo");
        funk.funkosPorModelo().forEach((k, v) -> System.out.println("Modelo "+k + " " + v));

        System.out.println("Numero de funkos por modelo");
        funk.numFunkosPorModelo().forEach((k, v) -> System.out.println("Modelo "+k + " " + v));



    }
}