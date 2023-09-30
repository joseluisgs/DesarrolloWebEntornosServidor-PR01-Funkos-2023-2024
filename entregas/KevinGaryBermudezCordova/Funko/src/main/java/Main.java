package org.example;

import controller.FunkoController;
import models.Funko;

public class Main {
    public static void main(String[] args) {
        System.out.println("Funkos");
        System.out.println("======");
        FunkoController controller = new FunkoController();
        controller.run();
    }
}