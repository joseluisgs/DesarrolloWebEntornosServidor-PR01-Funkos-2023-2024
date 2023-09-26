package controllers;

import database.models.Funko;
import enums.Modelo;
import services.FunkoService;
import utils.LocaleUtils;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class FunkoController {
    private final FunkoService funkoService;

    public FunkoController(FunkoService funkoService) {
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

    public void exportToJson() {
        funkoService.backup("data/funko.json");
    }
}
