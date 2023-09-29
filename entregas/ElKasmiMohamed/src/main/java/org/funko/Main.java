package org.funko;
import org.funko.models.Producto;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import static org.funko.services.database.CSVReader.readCSV;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {
        System.out.println("Lectura CSV");
        String filePath = "C:\\Users\\elkas\\Trabajos_EnServidor\\ElKasmiMohamed\\data\\funkos.csv";
        try {
            List<Producto> productos = readCSV(filePath);

            for (Producto producto : productos) {
                System.out.println(producto.toString());
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }




    }
}