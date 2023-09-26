package com.madirex;

import com.madirex.repositories.FunkoRepositoryImpl;
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
