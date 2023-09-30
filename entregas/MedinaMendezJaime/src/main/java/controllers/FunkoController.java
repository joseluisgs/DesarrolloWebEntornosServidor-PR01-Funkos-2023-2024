package controllers;

import Models.FunkoBD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import enums.Modelo;
import lombok.Getter;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

@Getter
public class FunkoController {
    private static FunkoController instance;
    private final List<FunkoBD> funkos = new ArrayList<>();

    public static FunkoController getInstance() {
        if (instance == null) {
            instance = new FunkoController();
        }
        return instance;
    }


    public FunkoController() {
        if (instance == null) {
            instance = this;
        }
    }

    public void loadCsv() {
        try (BufferedReader br = new BufferedReader(new FileReader("src" + File.separator + "data" + File.separator + "funkos.csv"))) {
            String line = br.readLine();
            line = br.readLine();
            while (line != null) {
                String[] split = line.split(",");

                int year = Integer.parseInt(split[4].split("-")[0]);
                int month = Integer.parseInt(split[4].split("-")[1]);
                int day = Integer.parseInt(split[4].split("-")[2]);

                LocalDate dia = LocalDate.of(year, month, day);

                UUID cod = UUID.fromString(split[0].substring(0, 35));

                funkos.add(new FunkoBD(UUID.randomUUID(), cod, split[1], Modelo.valueOf(split[2]), Double.parseDouble(split[3]), dia));
                line = br.readLine();
            }

        } catch (IOException e) {
            System.out.println("Error al leer el archivo CSV: " + e.getMessage());
        }
    }

    public void exportJson() {
        String rutaDelArchivo = "src" + File.separator + "data" + File.separator + "funkos.json";

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        try (FileWriter writer = new FileWriter(rutaDelArchivo)) {
            for (FunkoBD funko : funkos) {
                new FunkoBD(funko.getId(), funko.getCod(), funko.getNombre(), funko.getModelo(), funko.getPrecio(), funko.getFechaLanzamiento());
            }
            gson.toJson(funkos, writer);
        } catch (IOException e) {
            System.out.println("Error al escribir el archivo JSON: " + e.getMessage());
        }
    }

    public void showFunkos() {
        for (FunkoBD funko : funkos) {
            System.out.println(funko);
        }
    }

    public Optional<FunkoBD> findExpensive() {
        return funkos.stream().max((funko1, funko2) -> (int) (funko1.getPrecio() - funko2.getPrecio()));
    }

    public Double averagePrice() {
        return funkos.stream().mapToDouble(FunkoBD::getPrecio).average().orElse(0);
    }

    public Map<Modelo, Optional<FunkoBD>> getFunkosByModelo() {
        return funkos.stream().collect(groupingBy(FunkoBD::getModelo, Collectors.maxBy(Comparator.comparing(FunkoBD::getPrecio))));
    }

    public Map<Modelo, Double> getAveragePriceByModelo() {
        return funkos.stream().collect(groupingBy(FunkoBD::getModelo, Collectors.averagingDouble(FunkoBD::getPrecio)));
    }

    public List<FunkoBD> getFunkosLaunched2023() {
        return funkos.stream().filter(funko -> funko.getFechaLanzamiento().getYear() == 2023).collect(Collectors.toList());
    }

    /*Numero de funkos de Stitch y listado de ellos.*/
    public Map<String, Long> getStitchList() {
        return funkos.stream().filter(funko -> funko.getNombre().contains("Stitch")).collect(groupingBy(FunkoBD::getNombre, counting()));
    }

}
