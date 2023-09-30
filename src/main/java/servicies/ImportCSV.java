package servicies;

import models.Funko;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class ImportCSV {
List<Funko> funkoscsv;
    public ImportCSV() {
        mostrarCsv();
    }

    private String csv(){
        Path currentRelativePath = Paths.get("");
        String ruta = currentRelativePath.toAbsolutePath().toString();
        String dir = ruta + File.separator + "data";
        String paisesFile = dir + File.separator + "funkos.csv";
        return paisesFile;
    }
    private void mostrarCsv(){
        try (BufferedReader reader = new BufferedReader(new FileReader(csv()))) {
            Stream<Funko> funkos = reader.lines()
                    .map(linea -> linea.split(",")) // separar cada línea en un array de strings
                    .map(valores -> new Funko(UUID.fromString(valores[0].substring(0, valores.length-1)),valores[1],valores[3],Double.parseDouble(valores[4]), LocalDateTime.parse(valores[5])));
            funkos.forEach(System.out::println);
            funkoscsv= (List<Funko>) funkos;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
