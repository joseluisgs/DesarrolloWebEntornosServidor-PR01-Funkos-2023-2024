package services;

import database.manager.DatabaseManager;
import database.models.Funko;
import database.models.FunkoDB;
import lombok.extern.log4j.Log4j2;
import repository.FunkoRepository;
import repository.IFunkoRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;

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
}
