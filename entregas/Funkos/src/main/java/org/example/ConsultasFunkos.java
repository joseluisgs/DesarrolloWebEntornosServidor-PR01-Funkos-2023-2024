package org.example;

import org.example.models.Funkos;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ConsultasFunkos {
    public static void main(String[] args) throws IOException {
        List<String> funkos = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader("Data/funkos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                funkos.add(linea);
            }
        } catch (IOException exception) {
            System.out.println("No se ha encontrado ningún archivo en esta ruta o ha ocurrido un error al leer el archivo.");
        }

        for (String jsonLine : funkos) {
            System.out.println(jsonLine);
        }
    }
}
