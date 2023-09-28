package com.madirex;

import com.madirex.exceptions.FunkoException;
import com.madirex.exceptions.ReadCSVFailException;
import com.madirex.models.Funko;
import com.madirex.models.Model;
import com.madirex.repositories.FunkoRepositoryImpl;
import com.madirex.services.crud.funkos.FunkoService;
import com.madirex.services.crud.funkos.FunkoServiceImpl;
import com.madirex.services.database.DatabaseManager;
import com.madirex.services.io.CsvManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
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
            printFindAll(serv);
            printFindByName(serv, "Doctor Who Tardis");
            printFindById(serv, "3b6c6f58-7c6b-434b-82ab-01b2d6e4434a");
            List<Funko> funkosSave = printSaveAndGet(serv);

            // UPDATE //TODO: serv.update();
            if (!funkosSave.isEmpty()){
                System.out.println("\nUpdate:");
                try {
                    serv.update(funkosSave.get(0).getCod().toString(), Funko.builder()
                            .name("MadiFunkoModified")
                            .model(Model.DISNEY)
                            .price(42.42)
                            .releaseDate(LocalDate.now())
                            .build()).ifPresent(System.out::println);
                } catch (FunkoException e) {
                    throw new RuntimeException(e);
                }
            }
            printFindByName(serv, "MadiFunkoModified"); //TODO: ELIMINAR SI VEO UE SE UPDATEA BIEN

            //TODO: serv.delete();
            System.out.println("\nDelete:");
            try {
                serv.delete(funkosSave.get(0).getCod().toString());
            } catch (FunkoException e) {
                throw new RuntimeException(e);
            }

            printFindByName(serv, "MadiFunkoModified"); //TODO: ELIMINAR SI VEO UE SE UPDATEA BIEN


            doBackupAndPrint(serv);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void doBackupAndPrint(FunkoService serv) {
        System.out.println("\nBackup:");
        serv.backup(System.getProperty("user.dir") + File.separator + "data", "backup.json");
    }

    private List<Funko> printSaveAndGet(FunkoService serv) throws SQLException {
        //SAVE
        System.out.println("\nSave:");
        try {
            serv.save(Funko.builder()
                    .name("MadiFunko")
                    .model(Model.OTROS)
                    .price(42)
                    .releaseDate(LocalDate.now())
                    .build()).ifPresent(System.out::println);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
        return serv.findByName("MadiFunko");
    }

    private void printFindById(FunkoService serv, String id) throws SQLException {
        System.out.println("\nFind by Id:");
        serv.findById(id).ifPresent(System.out::println);
    }

    private void printFindByName(FunkoService serv, String name) throws SQLException {
        System.out.println("\nFind by Name:");
        serv.findByName(name).forEach(System.out::println);
    }

    private void printFindAll(FunkoService serv) throws SQLException {
        System.out.println("\nFind All:");
        serv.findAll().forEach(System.out::println);
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
