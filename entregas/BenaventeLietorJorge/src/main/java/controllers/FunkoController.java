package controllers;

import services.FunkoService;

public class FunkoController {
    private final FunkoService funkoService;

    public FunkoController(FunkoService funkoService) {
        this.funkoService = funkoService;
    }


    public void importCsv() {
        funkoService.importCsv();
    }
}
