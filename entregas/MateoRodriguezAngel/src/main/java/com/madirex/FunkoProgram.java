package com.madirex;

import com.madirex.repositories.FunkoRepositoryImpl;
import com.madirex.services.crud.funkos.FunkoService;
import com.madirex.services.crud.funkos.FunkoServiceImpl;
import com.madirex.services.database.DatabaseManager;
import com.madirex.services.io.CsvManager;

import java.io.File;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicBoolean;

public class FunkoProgram {

    private static FunkoProgram funkoProgramInstance;
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
            System.out.println("\nFind All:");
            serv.findAll().forEach(System.out::println);

            System.out.println("\nFind by Name:");
            serv.findByName("Doctor Who Tardis").forEach(System.out::println);

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
        csvManager.fileToFunkoList(path)
                .ifPresent(e -> {
                    e.forEach(funko -> {
                        try {
                            funkoRepository.save(funko);
                        } catch (SQLException throwables) {
                            System.out.println(throwables);
                            failed.set(true);
                        }
                    });

                    if (failed.get()) {
                        throw new RuntimeException("Error al insertar los datos en la base de datos");
                    }
                });
    }
}
