package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Consultas {
    
    public ArrayList<Funko> Funkomascaro(){
        ArrayList<Funko> listaFunkos = new ArrayList<Funko>();
        Funko funkMasCaro = listaFunkos.stream()
                .max(Comparator.comparingDouble(Funko::getPrecio))
                .orElse(null);
        // Si la lista está vacía, devuelve null
        return listaFunkos;
    }

    public ArrayList<Funko> MediadePreciosFunko(){
        ArrayList<Funko> listaFunkos = new ArrayList<Funko>();
        double mediaPrecio = listaFunkos.stream()
                .mapToDouble(Funko::getPrecio)
                .average()
                .orElse(0.0);

        System.out.println("Media de precio de funkos: " + mediaPrecio + " euros");
        return listaFunkos;
    }
    public ArrayList<Funko> Funkospormodelo() {
        ArrayList<Funko> listaFunkos = new ArrayList<Funko>();
        Map<String, List<Funko>> funkosPorModelo = listaFunkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo));

        System.out.println("Funkos agrupados por modelos: ");
        funkosPorModelo.forEach((modelo, listFunks) -> {
            System.out.println(modelo + ": " + listFunks.size() + " funkos");
        });

        return listaFunkos;
    }

    public ArrayList<Funko> NumeroFunkospormodelo(){
        ArrayList<Funko> listaFunkos = new ArrayList<Funko>();
        Map<String, Long> numeroFunkosPorModelo = listaFunkos.stream()
                .collect(Collectors.groupingBy(Funko::getModelo, Collectors.counting()));

        System.out.println("Número de funkos por modelos: ");
        numeroFunkosPorModelo.forEach((modelo, cantidad) -> {
            System.out.println(modelo + ": " + cantidad + " funkos");
        });

        return listaFunkos;
    }

    public ArrayList<Funko> Funko2023(){
        ArrayList<Funko> listafunkos =new ArrayList<Funko>();
        Predicate<Funko> lanzadosEn2023 = funko -> funko.getFechaLanzamiento().getYear() == 2023;

        List<Funko> funkosLanzadosEn2023 = listafunkos.stream()
                .filter(lanzadosEn2023)
                .collect(Collectors.toList());

        System.out.println("Funkos lanzados en 2023: ");
        funkosLanzadosEn2023.forEach(funko -> {
            System.out.println(funko.getNombre());
        });

        return listafunkos;
    }

    public ArrayList<Funko>FunkoStich(){
        ArrayList<Funko> listaFunkos = new ArrayList<Funko>();
        String nombreBuscado = "Stitch";

        Predicate<Funko> contieneNombre = funko -> funko.getNombre().contains(nombreBuscado);

        List<Funko> funkosDeStitch = listaFunkos.stream()
                .filter(contieneNombre)
                .collect(Collectors.toList());

        System.out.println("Número de funkos de Stitch: " + funkosDeStitch.size());
        System.out.println("Listado de funkos de Stitch: ");
        funkosDeStitch.forEach(funko -> {
            System.out.println(funko.getNombre());
        });

        return listaFunkos;
    }

}

