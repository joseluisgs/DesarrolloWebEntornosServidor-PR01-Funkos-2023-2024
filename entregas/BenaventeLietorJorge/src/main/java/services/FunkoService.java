package services;

import database.manager.DatabaseManager;
import database.models.Funko;
import enums.Modelo;
import lombok.extern.log4j.Log4j2;
import repository.FunkoRepository;
import repository.IFunkoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class FunkoService {
    private static FunkoService instance;

    public static FunkoService getInstance() {
        if (instance == null) {
            instance = new FunkoService(FunkoRepository.getInstance(DatabaseManager.getInstance()));
        }
        return instance;
    }

    private final IFunkoRepository funkoRepository;

    private FunkoService(IFunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
    }

    public void importCsv() {
        final String fileName = "data/funkos.csv";
        try {
            List<Funko> funkos = Files.lines(Path.of(fileName))
                    .skip(1)
                    .map(line -> line.split(","))
                    .map(Funko::fromCsv).toList();
            funkoRepository.saveAll(funkos);
        } catch (IOException e) {
            log.error("Error al leer el fichero csv", e);
        }
    }

    public Funko getMostExpensiveFunko() {
        List<Funko> funkos = funkoRepository.findAll();
        return funkos.stream().max(Comparator.comparingDouble(Funko::getPrecio)).orElse(null);
    }

    public double getAvgPrice() {
        List<Funko> funkos = funkoRepository.findAll();
        return funkos.stream().mapToDouble(Funko::getPrecio).average().orElse(0);
    }

    public Map<Modelo, List<Funko>> getGroupedByModels() {
        List<Funko> funkos = funkoRepository.findAll();
        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo));
    }

    public Map<Modelo, Long> getCountByModels() {
        List<Funko> funkos = funkoRepository.findAll();
        return funkos.stream().collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));
    }

    public List<Funko> getLaunchedIn2023() {
        List<Funko> funkos = funkoRepository.findAll();
        return funkos.stream().filter(funko -> funko.getFechaLanzamiento().getYear() == 2023).toList();
    }

    public Map.Entry<Integer, List<Funko>> getStitchCountAndList() {
        List<Funko> funkos = funkoRepository.findAll();
        List<Funko> stitchFunkos = funkos.stream().filter(f -> f.getNombre().contains("Stitch")).toList();
        return Map.entry(stitchFunkos.size(), stitchFunkos);
    }

}
