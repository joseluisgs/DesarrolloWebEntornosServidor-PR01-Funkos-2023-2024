package org.funko.services.database;

import org.funko.models.Producto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CSVReader {

    public static List<Producto> readCSV(String filePath) throws IOException, ParseException {
        List<Producto> productos = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            reader.readLine();

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    UUID cod = UUID.fromString(parts[0]) ;
                    String nombre = parts[1];
                    String modelo = parts[2];
                    double precio = Double.parseDouble(parts[3]);
                    Date fechaLanzamiento = dateFormat.parse(parts[4]);

                    Date createdAt = new Date();
                    Date updatedAt = new Date();

                    Producto producto = new Producto(0, cod, nombre, modelo, precio, fechaLanzamiento, createdAt, updatedAt);
                    productos.add(producto);
                }
            }
        }catch (Exception e){
            System.out.println("mal");
        }

        return productos;
    }

    public static void main(String[] args) {
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


