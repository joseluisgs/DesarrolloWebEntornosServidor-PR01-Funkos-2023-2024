package org.funkos;

import org.funkos.controllers.FunkoController;
import org.funkos.models.Funko;
import org.funkos.repositories.funko.FunkoRepositoryImpl;
import org.funkos.services.backup.BackUpToJsonImpl;
import org.funkos.services.database.DataBaseManager;
import org.funkos.services.files.FunkosCsvManager;
import org.funkos.services.funkos.FunkoServiceImpl;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Path ruta = Paths.get("");
        String ruta_absoluta = ruta.toAbsolutePath().toString() + File.separator + "data" ;
        String ruta_file = ruta_absoluta + File.separator + "funkos.csv";
        FunkosCsvManager managerFunkos = new FunkosCsvManager();
        List<Funko> funkos = managerFunkos.readAllCSV(ruta_file);
        System.out.println(funkos);

        //FunkoController controllerFunko = new FunkoController(FunkoRepositoryImpl.getInstance(DataBaseManager.getInstance(), BackUpToJsonImpl.getInstance()));
        //FunkoServiceImpl controllerFunko = FunkoServiceImpl.getInstance(FunkoRepositoryImpl.getInstance(DataBaseManager.getInstance()), 25,  BackUpToJsonImpl.getInstance());
        FunkoController controllerFunko = new FunkoController(FunkoServiceImpl.getInstance(FunkoRepositoryImpl.getInstance(DataBaseManager.getInstance(), BackUpToJsonImpl.getInstance()), 25));

        for(Funko funko : funkos){
            controllerFunko.insertFunko(funko);
        }

        System.out.println();
        System.out.println("-----------------CONSULTAS-----------------");
        System.out.println();

        System.out.println("Todos los funkos");
        var funkosAll = controllerFunko.findAll();
        System.out.println(funkosAll);
        System.out.println();

        System.out.println("Funko mas caro");
        var funkoMasCaro = funkosAll.stream()
                .max(Comparator.comparingDouble(Funko::getPrecio)).orElse(null);
        System.out.println(funkoMasCaro);
        System.out.println();

        System.out.println("Media de los precios");
        var mediaPrecios = funkosAll.stream()
                .mapToDouble(Funko::getPrecio)
                .average().orElse(0);
        System.out.println(mediaPrecios);
        System.out.println();

        System.out.println("Funkos agrupados por modelos");
        var funkosAgrupadosPorModelos = funkosAll.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));
        System.out.println(funkosAgrupadosPorModelos);
        System.out.println();

        System.out.println("Numero de funkos por modelo");
        var numeroFunkosPorModelo = funkosAll.stream()
                .collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));
        System.out.println(numeroFunkosPorModelo);
        System.out.println();

        System.out.println("Funkos lanzados en 2023");
        var funkosLanzadosEn2023 = funkosAll.stream()
                .filter(funko -> funko.getFecha().getYear() == 2023)
                .collect(Collectors.toList());
        System.out.println(funkosLanzadosEn2023);
        System.out.println();

        System.out.println("Funkos de stitch y cuantos");
        var funkosStichYconteo = funkosAll.stream()
                .filter(funko -> funko.getNombre().contains("Stitch"))
                .collect(Collectors.groupingBy(Funko::getNombre, Collectors.counting()));
        System.out.println(funkosStichYconteo);
        System.out.println();

        controllerFunko.backup();

    }
}