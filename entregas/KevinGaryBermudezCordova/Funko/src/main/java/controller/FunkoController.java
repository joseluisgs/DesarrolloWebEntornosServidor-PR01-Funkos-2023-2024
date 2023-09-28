package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;
import lombok.val;
import models.Funko;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

@Data
public class FunkoController {
    @val
    List<Funko> funko ;
    private void loadData() {

    @val
    String dataPath = "data" + File.separator + "funkos.csv";
    @val String appPath = System.getProperty("user.dir");
    @val
    Path filePath = Paths.get(appPath + File.separator + dataPath);
        System.out.println("Loading data from: " + filePath);
    // Existe usando Paths
        if (Files.exists(filePath)) {
        System.out.println("File data exists");
    } else {
        System.out.println("File data does not exist");
    }

    // Leemos los datos...
        try {
            funko = Files.lines(filePath)
                .skip(1)
                .map(this::getFunko)
                .toList();
    } catch (Exception e) {
        System.out.println("Error reading file: " + e.getMessage());
    }
}
    private Funko getFunko(String line) {
        String[] parts = line.split(",");

        String cod = line.substring(0, 36).trim(); // Tomar los primeros 36 caracteres, luego eliminar espacios en blanco
        String nombre = parts[1];
        String modelo = parts[2];
        Double precio = Double.valueOf(parts[3]);


        LocalDate fecha = LocalDate.parse(parts[4]);

        return new Funko(cod, nombre, modelo, precio, fecha);
    }


    public void run() {
        loadData();
        funko.forEach(System.out::println);

       exportToJson();

    }
    public void exportToJson() {
        String fileName = "funkos.json";
        Path jsonFilePath = Paths.get(fileName);

        try (FileWriter writer = new FileWriter(String.valueOf(jsonFilePath))) {
            Gson gson = new GsonBuilder()
                    .setPrettyPrinting()
                    .excludeFieldsWithoutExposeAnnotation() // Excluir campos sin la anotación @Expose
                    .create();
            String json = gson.toJson(funko);
            writer.write(json);
            System.out.println("Data exported to JSON successfully.");
        } catch (IOException e) {
            System.out.println("Error exporting data to JSON: " + e.getMessage());
        }
    }
    }





