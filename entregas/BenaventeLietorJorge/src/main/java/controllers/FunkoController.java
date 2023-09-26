package controllers;

import database.models.Funko;
import services.FunkoService;
import utils.LocaleUtils;

import java.text.NumberFormat;
import java.util.Locale;

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


}
