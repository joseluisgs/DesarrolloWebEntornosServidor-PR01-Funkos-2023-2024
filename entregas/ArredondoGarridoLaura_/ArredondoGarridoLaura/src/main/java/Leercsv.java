import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import model.Funko;

public class Leercsv {
    String path;

    public ArrayList<Funko> read() throws CsvValidationException, IOException {
        ArrayList<Funko> juguetes = new ArrayList<Funko>();
        String[] archivos = {"funkos.csv"};
        for (String archivo : archivos) {
            path = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "main" + File.separator + "csv" + File.separator + archivo;

            try (CSVReader reader = new CSVReader(new FileReader(path))) {
                reader.readNext();
                String []line;
                reader.readNext();
                while ((line = reader.readNext())!= null) {
                    Funko funko = new Funko();
                        funko.setCOD(UUID.fromString(line[0].length() > 36 ? line[0].substring(0, 36) : line[0]));
                        funko.setNombre(line[1]);
                        funko.setModelo(line[2]);
                        funko.setPrecio(Double.parseDouble(line[3]));
                        funko.setFechaLanzamiento(LocalDate.parse(line[4], DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                        juguetes.add(funko);
                    }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!juguetes.isEmpty()) {
            for (Funko funk : juguetes) {
                System.out.println("CODIGO/COD: " + funk.getCOD());
                System.out.println("Nombre: " + funk.getNombre());
                System.out.println("Modelo: " + funk.getModelo());
                System.out.println("Precio: " + funk.getPrecio());
                System.out.println("Fecha de Lanzamiento: " + funk.getFechaLanzamiento());
                System.out.println("-------------------------------------------");
            }
        }

        return juguetes;
    }
    public static void main(String[] args) throws CsvValidationException, IOException {
        Leercsv ma = new Leercsv();
        ma.read();
    }
}

