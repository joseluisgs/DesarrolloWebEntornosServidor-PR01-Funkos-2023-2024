package services;

import database.models.Funko;
import enums.Modelo;

import java.util.List;
import java.util.Map;

public interface IFunkoService {
    void importCsv();

    Funko getMostExpensiveFunko();

    double getAvgPrice();

    Map<Modelo, List<Funko>> getGroupedByModels();

    Map<Modelo, Long> getCountByModels();

    List<Funko> getLaunchedIn2023();

    Map.Entry<Integer, List<Funko>> getStitchCountAndList();

    void backup(String ruta);

    Funko findByNombre(String nombre);
}
