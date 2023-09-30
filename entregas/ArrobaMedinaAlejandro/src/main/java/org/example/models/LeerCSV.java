package org.example.models;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class LeerCSV {

    public static final String SEPARADOR = ",";
    BufferedReader bufferLectura = null;
    try {
        // Abrir el .csv en buffer de lectura
        try {
            bufferLectura = new BufferedReader(new FileReader("archivo.csv"));
        } catch (FileNotFoundException ex) {
            throw new RuntimeException(ex);
        }

        // Leer una linea del archivo
        String linea = null;
        try {
            linea = bufferLectura.readLine();
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        while (linea != null) {
            // Sepapar la linea leída con el separador definido previamente
            String[] campos = linea.split(SEPARADOR);

            System.out.println(Arrays.toString(campos));

            // Volver a leer otra línea del fichero
            try {
                linea = bufferLectura.readLine();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
    }

}
