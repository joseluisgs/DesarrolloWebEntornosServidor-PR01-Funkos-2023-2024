package dev.services;

import dev.managers.DatabaseManager;
import dev.models.Funko;
import dev.models.Modelo;
import dev.repositories.FunkosRepoImpl;
import dev.services.db.FunkoDBImpl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class FunkoService {

    final String CSV_SEPARATOR = ",";
    DatabaseManager dbManager;
    FunkoDBImpl funkoDB;


    public FunkoService() {
        this.dbManager = DatabaseManager.getInstance();
        funkoDB = FunkoDBImpl.getInstance(new FunkosRepoImpl(dbManager));
    }

    public List<Funko> getFunkos() throws SQLException, IOException {
        return funkoDB.findAll();
    }

    private String getDataDir() {

        String userDir = System.getProperty("user.dir");
        Path currentRelativePath = Paths.get(userDir);
        String ruta = currentRelativePath.toAbsolutePath().toString();
        return ruta + File.separator + "data";
    }

    public void importCSV() throws IOException, SQLException {

        String dir = getDataDir();
        String funkosCSV = dir+File.separator+ "funkos.csv";
        Path filePath = Paths.get(funkosCSV);
        if (!Files.exists(filePath)) {
            throw new FileNotFoundException("File not found: " + funkosCSV);
        }
        List<Funko> funkos = Files.readAllLines(filePath).stream().skip(1).map(lin ->{
            String[] fields = lin.split(CSV_SEPARATOR);
            String uuid = fields[0];
            Funko fun = new Funko(UUID.fromString(uuid.substring(0, 35)), fields[1], Modelo.valueOf(fields[2]), Double.parseDouble(fields[3]), LocalDate.parse(fields[4]));
            return fun;
        }).toList();

        for (Funko funko : funkos) {
            funkoDB.save(funko);
        }



    }

    public Optional<Funko> funkoMasCaro() throws SQLException, IOException {

        List<Funko> funkos = getFunkos();

        return funkos.stream().max(Comparator.comparingDouble(Funko::getPrecio));

    }

    public double mediaPrecioFunkos() throws SQLException, IOException {
        List<Funko> funkos = getFunkos();
        return funkos.stream().mapToDouble(Funko::getPrecio).average().orElse(0.0);
    }

    public Map<Modelo, List<Funko>> funkosPorModelo () throws SQLException, IOException {

        List<Funko> funkos = getFunkos();

        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo));
    }

    public Map<Modelo, Integer> numFunkosPorModelo () throws SQLException, IOException {

        List<Funko> funkos = getFunkos();

        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo, Collectors.summingInt(e -> 1)));

    }

    public List<Funko> funkosLanzadosEnAnoEspecifico(int ano) throws SQLException, IOException {

        List<Funko> funkos = getFunkos();

        return funkos.stream().filter(fk -> fk.getFechaLanzamiento().getYear() == ano).toList();

    }

    public List<Funko> funkosContienenPalabra(String palabra) throws SQLException, IOException {

        List<Funko> funkos = getFunkos();

        return funkos.stream().filter(fk -> fk.getNombre().toLowerCase().contains(palabra.toLowerCase())).toList();

    }

    public boolean backup(String path) throws SQLException, IOException {
        return funkoDB.backup(path);
    }

}
