package controllers;

import database.models.Funko;
import services.FunkoService;

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


}
