package controllers;

import models.Funko;
import models.Modelo;

import java.io.*;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunkosControllers {
    private static FunkosControllers instance;
    private final String dir = "src" + File.separator + "data" + File.separator + "funkos.csv";

    private List<Funko> funkos = new ArrayList<>();

    public static FunkosControllers getInstance() {
        if (instance == null) {
            instance = new FunkosControllers();
        }
        return instance;
    }

    public void loadFunkos() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(dir));
            String line = br.readLine();
            line = br.readLine();
            while (null != line) {

                String[] fields = line.split(",");
                UUID cod = UUID.fromString(fields[0].substring(0,35));
                String nombre = fields[1];
                Modelo modelo = Modelo.valueOf(fields[2]);
                double precio = Double.parseDouble(fields[3]);
                LocalDate fecha_lanzamiento = LocalDate.parse(fields[4]);
                funkos.add(new Funko(UUID.randomUUID(), cod, nombre, modelo, precio, fecha_lanzamiento));
                line = br.readLine();
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo: " + e.getMessage());
        }
    }

    public String funkoMasCaro(){
       return funkos.stream().
                max((funko1, funko2) -> (int) (funko1.getPrecio() - funko2.getPrecio()))
               .map(Funko::getNombre)
                .orElse("No hay funkos");
    }

    public double mediaPrecios(){
        return funkos.stream()
                .mapToDouble(Funko::getPrecio)
                .average()
                .orElse(0);
    }
    
}