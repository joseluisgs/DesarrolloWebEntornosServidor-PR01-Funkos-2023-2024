package controllers;

import database.models.Funko;
import enums.Modelo;
import exceptions.FunkoNotFoundException;
import lombok.extern.log4j.Log4j2;
import services.IFunkoService;
import utils.LocaleUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Log4j2
public class FunkoController {
    private final IFunkoService funkoService;

    public FunkoController(IFunkoService funkoService) {
        this.funkoService = funkoService;
    }


    public void importCsv() {
        funkoService.importCsv();
    }

    public Funko getMostExpensiveFunko() {
        return funkoService.getMostExpensiveFunko();
    }

    public String getAvgPrice() {
        Locale locale = LocaleUtils.getLocale();
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        return currencyFormatter.format(funkoService.getAvgPrice());
    }

    public Map<Modelo, List<Funko>> getGroupedByModels() {
        return funkoService.getGroupedByModels();
    }

    public Map<Modelo, Long> getCountByModels() {
        return funkoService.getCountByModels();
    }

    public List<Funko> getLaunchedIn2023() {
        return funkoService.getLaunchedIn2023();
    }

    public Map.Entry<Integer, List<Funko>> getStitchCountAndList() {
        return funkoService.getStitchCountAndList();
    }

    public Funko findByNombre(String nombre) {
        try {
            return funkoService.findByNombre(nombre);
        } catch (FunkoNotFoundException e) {
            log.error("Error al buscar el funko por nombre", e);
            return null;
        }
    }

    public void exportToJson() {
        funkoService.backup("data/funko.json");
    }

}
