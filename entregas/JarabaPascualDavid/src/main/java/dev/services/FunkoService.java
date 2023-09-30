package dev.services;

import com.google.gson.Gson;
import dev.managers.DatabaseManager;
import dev.models.Funko;
import dev.models.Modelo;
import dev.repositories.FunkosRepoImpl;

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
    FunkosRepoImpl repository;


    public FunkoService(DatabaseManager dbManager) throws FileNotFoundException {
        this.dbManager = dbManager;
        this.repository = new FunkosRepoImpl(dbManager);
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
            if (fields[0].length() > 36)
                uuid = uuid.substring(0,36);
            Funko fun = new Funko(UUID.fromString(uuid), fields[1], Modelo.valueOf(fields[2]), Double.parseDouble(fields[3]), LocalDate.parse(fields[4]));
            return fun;
        }).toList();

        for (Funko funko : funkos) {
            repository.save(funko);
        }



    }

    public Optional<Funko> funkoMasCaro() throws SQLException, IOException {
        List<Funko> funkos = repository.findAll();

        return funkos.stream().max(Comparator.comparingDouble(Funko::getPrecio));

    }


    public double mediaPrecioFunkos() throws SQLException, IOException {

        List<Funko> funkos = repository.findAll();

        return funkos.stream().mapToDouble(Funko::getPrecio).average().getAsDouble();

    }

    public Map<Modelo, List<Funko>> funkosPorModelo () throws SQLException, IOException {

        List<Funko> funkos = repository.findAll();

        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo));
    }


    public Map<Modelo, Integer> numFunkosPorModelo () throws SQLException, IOException {

        List<Funko> funkos = repository.findAll();

        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo, Collectors.summingInt(e -> 1)));

    }

    public void exportJSON() throws SQLException, IOException {

        List<Funko> funkos = repository.findAll();

        String dir = getDataDir();
        String funkosJsonFile = dir + File.separator + "funkos.json";

        Path filePath = Paths.get(funkosJsonFile);

        if (!Files.exists(filePath)) {
            Files.createFile(filePath);
        }

        Gson gson = new Gson();

        gson.toJson(funkos, Files.newBufferedWriter(filePath));

    }

}
