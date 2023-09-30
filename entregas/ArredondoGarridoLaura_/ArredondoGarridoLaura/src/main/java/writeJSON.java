import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.opencsv.exceptions.CsvValidationException;
import model.Funko;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import adaptador.LocalDateAdapter;
import java.util.List;

public class writeJSON {

    public boolean writeJSON(String routePath, List<Funko> funks) {
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "src"+ File.separator + "main" + File.separator+ File.separator + "json" + routePath;
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .setPrettyPrinting()
                .create();
        boolean success = false;
        try (FileWriter writer = new FileWriter(path)) {
            gson.toJson(funks, writer);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void main(String[] args) throws CsvValidationException, IOException {
        Leercsv ls = new Leercsv();
        var list = ls.read();
        writeJSON e = new writeJSON();
        e.writeJSON("Funkos.json", list);
    }
}