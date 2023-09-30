package exercise.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class consultasFunkos {
    public static void main(String[] args) throws IOException{
        List<String> listaFunkos = new ArrayList<>(); //Creo una lista para almacenar el csv

        try (BufferedReader br = new BufferedReader(new FileReader("data/funkos.csv"))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                listaFunkos.add(linea); //Leo el csv y meto todas las líneas en la lista
            }
        } catch (IOException exception) {//Pongo una excepción para en caso de que no se encuentre ningún archivo
            System.out.println("No se ha encontrado ningún archivo en esta ruta o ha ocurrido un error al leer el archivo.");
        }

        // Mostrar las líneas del archivo CSV
        for (String linea : listaFunkos) {
            System.out.println(linea);
        }
    }
}
