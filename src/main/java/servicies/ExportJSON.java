package servicies;

import com.google.gson.Gson;

import models.Funko;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ExportJSON {

    private String json(){
        Path currentRelativePath = Paths.get("");
        String ruta = currentRelativePath.toAbsolutePath().toString();
        String dir = ruta + File.separator + "data";
        String paisesFile = dir + File.separator + "funkos.json";
        return paisesFile;
    }
    private void crearJSON(){
        Gson gson = new Gson();
        List<Funko> funkos = null;
        try (FileWriter writer = new FileWriter(json())) {
            // Escribimos la lista de pokemons en el archivo JSON
            gson.toJson(funkos, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
