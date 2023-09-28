package com.madirex;

import com.madirex.controllers.FunkoController;
import com.madirex.exceptions.FunkoException;
import com.madirex.exceptions.FunkoNotFoundException;
import com.madirex.exceptions.FunkoNotValidException;
import com.madirex.exceptions.ReadCSVFailException;
import com.madirex.models.Funko;
import com.madirex.models.Model;
import com.madirex.repositories.funko.FunkoRepositoryImpl;
import com.madirex.services.crud.funko.FunkoService;
import com.madirex.services.crud.funko.FunkoServiceImpl;
import com.madirex.services.database.DatabaseManager;
import com.madirex.services.io.CsvManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class FunkoProgram {

    private static FunkoProgram funkoProgramInstance;
    private final Logger logger = LoggerFactory.getLogger(FunkoProgram.class);
    private FunkoController controller;


    private FunkoProgram() {
        controller = new FunkoController(new FunkoServiceImpl(new FunkoRepositoryImpl(DatabaseManager.getInstance())));
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
        //TODO: hacer todo lo de abajo pero con controlador (controller) y eliminar lo del serv
        try {
            //Casos correctos
            printFindAll();
            printFindByName("Doctor Who Tardis");
            printFindById("3b6c6f58-7c6b-434b-82ab-01b2d6e4434a");
            printSave("MadiFunko");
            printUpdate("MadiFunko", "MadiFunkoModified");
            printDelete("MadiFunkoModified");
            doBackupAndPrint("data");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void printDelete(String name) throws SQLException {
        System.out.println("\nDelete:");
        try {
            controller.delete(controller.findByName(name).get(0).getCod().toString());
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
    }

    private void printUpdate(String name, String newName) throws SQLException {
        System.out.println("\nUpdate:");
        try {
            controller.update(controller.findByName(name).get(0).getCod().toString(), Funko.builder()
                    .name(newName)
                    .model(Model.DISNEY)
                    .price(42.42)
                    .releaseDate(LocalDate.now())
                    .build()).ifPresent(System.out::println);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
    }

    private void doBackupAndPrint(String rootFolderName) {
        System.out.println("\nBackup:");
        controller.backup(System.getProperty("user.dir") + File.separator + rootFolderName, "backup.json");
    }

    private void printSave(String name) throws SQLException {
        //SAVE
        System.out.println("\nSave:");
        try {
            controller.save(Funko.builder()
                    .name(name)
                    .model(Model.OTROS)
                    .price(42)
                    .releaseDate(LocalDate.now())
                    .build()).ifPresent(System.out::println);
        } catch (FunkoException e) {
            throw new RuntimeException(e);
        }
    }

    private void printFindById(String id) throws SQLException {
        System.out.println("\nFind by Id:");
        try {
            System.out.println(controller.findById(id));
        } catch (FunkoNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void printFindByName(String name) throws SQLException {
        System.out.println("\nFind by Name:");
        controller.findByName(name).forEach(System.out::println);
    }

    private void printFindAll() throws SQLException {
        System.out.println("\nFind All:");
        controller.findAll().forEach(System.out::println);
    }

    public void loadFunkosFileAndInsertToDatabase(String path) {
        AtomicBoolean failed = new AtomicBoolean(false);
        CsvManager csvManager = CsvManager.getInstance();
        try {
            csvManager.fileToFunkoList(path)
                    .ifPresent(e -> {
                        e.forEach(funko -> {
                            try {
                                controller.save(funko);
                            } catch (SQLException throwables) {
                                failed.set(true);
                                String strError = "Error: " + throwables;
                                logger.error(strError);
                            } catch (FunkoNotValidException ex) {
                                throw new RuntimeException(ex);
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
