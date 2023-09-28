package org.example.controllers;

import lombok.val;
import org.example.models.Funko;
import org.example.models.Modelo;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class FunkoController {
    List<Funko> funkos;
    @val
    private List<Funko>  loadData() {
        @val String dataPath = "data" + File.separator + "funkos.csv";
        @val String appPath = System.getProperty("user.dir");
        @val Path filePath = Paths.get(appPath + File.separator + dataPath);
        System.out.println("Loading data from: " + filePath);
        // Existe usando Paths
        if (Files.exists(filePath)) {
            System.out.println("File data exists");
        } else {
            System.out.println("File data does not exist");
        }

        try {
            funkos = Files.lines(filePath)
                    .skip(1)
                    .map(this::getFunko)
                    .toList();
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        return funkos;
    }

    private Funko getFunko(String linea) {
        String[] campos = linea.split(",");
        UUID uuid = campos[0].length() > 36 ? UUID.fromString(campos[0].substring(0,36)): UUID.fromString(campos[0]);
        String nombre = campos[1];
        Modelo modelo = Modelo.valueOf(campos[2]);
        double precio = Double.parseDouble(campos[3]);
        LocalDate fecha_lanzamiento = getFecha(campos[4]);
        return new Funko(uuid, nombre, modelo, precio, fecha_lanzamiento);
    }

    private LocalDate getFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    public static void main(String[] args) {
        FunkoController fc = new FunkoController();
        List <Funko> list = fc.loadData();
        list.forEach(System.out::println);
    }
}
