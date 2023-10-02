package dev;

import dev.controllers.FunkoController;
import dev.services.FunkoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws IOException, SQLException {

        FunkoController funk = new FunkoController(new FunkoService());
        funk.importCSV();

        funk.backup("data/funkos.json");

        Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("Funko mas caro");
        logger.info(funk.funkoMasCaro().toString());

        logger.info("Media de precio de funkos");
        logger.info(Double.toString(funk.mediaPrecioFunkos()));

        logger.info("Funkos por modelo");
        funk.funkosPorModelo().forEach((k, v) -> logger.info("Modelo "+k + " " + v));

        logger.info("Numero de funkos por modelo");
        funk.numFunkosPorModelo().forEach((k, v) -> logger.info("Modelo "+k + " " + v));

        logger.info("Funkos lanzados en 2023");
        funk.funkosLanzadosEnAnoEspecifico(2023).forEach(System.out::println);

        logger.info("Funkos de Stitch");
        funk.funkosContienenPalabra("stitch").forEach(System.out::println);
        logger.info("Numero de funkos de Stitch "+funk.numFunkosDe("stitch"));

    }
}