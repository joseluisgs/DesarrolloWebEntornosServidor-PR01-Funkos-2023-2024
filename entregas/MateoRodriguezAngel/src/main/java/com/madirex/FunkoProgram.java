package com.madirex;

import com.madirex.exceptions.ReadCSVFailException;
import com.madirex.repositories.FunkoRepositoryImpl;
import com.madirex.services.crud.funkos.FunkoService;
import com.madirex.services.crud.funkos.FunkoServiceImpl;
import com.madirex.services.database.DatabaseManager;
import com.madirex.services.io.CsvManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FunkoProgram {

    private static FunkoProgram funkoProgramInstance;
    private final Logger logger = LoggerFactory.getLogger(FunkoProgram.class);
    private FunkoRepositoryImpl funkoRepository = new FunkoRepositoryImpl(DatabaseManager.getInstance());


    private FunkoProgram() {
    }

    public static FunkoProgram getInstance() {
        if (funkoProgramInstance == null) {
            funkoProgramInstance = new FunkoProgram();
        }
        return funkoProgramInstance;
    }

    public void init() {
        System.out.println("Programa de Funkos iniciado.");
        loadFunkosFileAndInsertToDatabase("data" + File.separator + "funkos.csv");
        callAllServiceMethods();
    }

    private void callAllServiceMethods() {
        FunkoService serv = FunkoServiceImpl.getInstance(new FunkoRepositoryImpl(DatabaseManager.getInstance()));
        try {
            System.out.println("Find All:");
            serv.findAll().forEach(System.out::println);

            System.out.println("Find by Name:");
            serv.findByName("Doctor Who Tardis");

            serv.backup(System.getProperty("user.dir") + File.separator + "data", "backup.json");
            //serv.findById();
            //serv.save();
            //serv.update();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void loadFunkosFileAndInsertToDatabase(String path) {
        AtomicBoolean failed = new AtomicBoolean(false);
        CsvManager csvManager = CsvManager.getInstance();
        try {
            csvManager.fileToFunkoList(path)
                    .ifPresent(e -> {
                        e.forEach(funko -> {
                            try {
                                funkoRepository.save(funko);
                            } catch (SQLException throwables) {
                                failed.set(true);
                                String strError = "Error: " + throwables;
                                logger.error(strError);
                            }
                        });

                        if (failed.get()) {
                            logger.error("Error al insertar los datos en la base de datos");
                        }
                    });
        } catch (ReadCSVFailException e) {
            logger.error("Error al leer el CSV");
        }
    }
}
