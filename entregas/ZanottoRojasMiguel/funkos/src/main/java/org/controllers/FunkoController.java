package org.controllers;

import org.models.Funko;
import org.models.Modelo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class FunkoController {
    List<Funko> funkos;


    private void loadData(){
        String dataPath = "data" + File.separator + "funkos.csv";
        String appPath = System.getProperty("user.dir");
        Path filePath = Paths.get(appPath + File.separator + dataPath);
        System.out.println("Loading data from: " + filePath);

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
    }

    public void run(){
        loadData();
        consultas();
    }

    public void consultas(){
        //funko mas caro
        double maxPrecio = funkos.stream().mapToDouble(Funko::getPrecio).max().orElse(0.0);
        Optional <Funko> funkoMasCaro = funkos.stream().filter(f -> f.getPrecio() == maxPrecio).findFirst();

        //media de precio de funkos
        double mediaPrecio = funkos.stream().mapToDouble(Funko::getPrecio).average().orElse(0.0);
        System.out.println(mediaPrecio);

        //Funkos agrupados por modelo
        Map<Modelo, List<Funko>> funkosPorModelo = funkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));
        funkosPorModelo.forEach((a, b) -> System.out.println(a.toString() + " -> " + b));

        //Número de funkos por modelo
        Map<Modelo, Long> numeroPorModelo = funkos.stream().collect(Collectors.groupingBy(
                Funko::getModelo,
                Collectors.counting()));
        numeroPorModelo.forEach((a, b) -> System.out.println(a.toString() + "->" + b));

        // Funkos que han sido lanzados en 2023
        List<Funko> funkos2023  = funkos.stream().filter(f -> f.getFechaLanzamiento().getYear() == 2023).toList();
        funkos2023.forEach(System.out::println);

        // Numero de funkos de Stitch y listado de ellos
        List<Funko> funkosStitch = funkos.stream().filter(f -> f.getNombre().contains("Stitch")).toList();
        System.out.println("Número de funkos de Stitch: " + funkosStitch.size());
        funkosStitch.forEach(System.out::println);
    }

    private void mostrarDatos(){
        funkos.forEach(System.out::println);
    }

    private Funko getFunko(String linea){
        String[] campos = linea.split(",");
        UUID COD = getUUID(campos[0]);
        String nombre = campos[1];
        Modelo modelo = Modelo.valueOf(campos[2]);
        double precio = Double.parseDouble(campos[3]);
        LocalDate fecha = getFecha(campos[4]);
        Funko funko = Funko.builder()
                        .COD(COD)
                        .nombre(nombre)
                        .modelo(modelo)
                        .precio(precio)
                        .fechaLanzamiento(fecha).build();
        return funko;
    }


    private LocalDate getFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(fecha, formatter);
    }

    private UUID getUUID(String uuid) {
        return uuid.length() > 36 ? UUID.fromString(uuid.substring(0,36)) : UUID.fromString(uuid);
    }


}
