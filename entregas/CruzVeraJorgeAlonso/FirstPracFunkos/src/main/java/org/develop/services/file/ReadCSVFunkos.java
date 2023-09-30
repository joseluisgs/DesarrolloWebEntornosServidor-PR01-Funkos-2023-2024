package org.develop.services.file;

import com.opencsv.CSVReader;
import org.develop.model.Funko;
import org.develop.model.Modelo;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

public class ReadCSVFunkos {


    public ArrayList<Funko> readFileFunko() {
        String path = Paths.get("").toAbsolutePath().toString() + File.separator + "data" + File.separator + "funkos.csv";
        ArrayList<Funko> funks = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try(CSVReader reader = new CSVReader(new InputStreamReader(new FileInputStream(path), StandardCharsets.UTF_8))){
            String[] line;
            reader.readNext();
        while ((line = reader.readNext()) != null){
            Funko fk = new Funko();
            fk.setUuid(UUID.fromString(line[0].length()>36?line[0].substring(0,35):line[0]));
            fk.setName(line[1]);
            fk.setModelo(Modelo.valueOf(line[2]));
            fk.setPrecio(Double.parseDouble(line[3]));
            fk.setFecha_lanzamiento(LocalDate.parse(line[4],formatter));
            funks.add(fk);
        }
        }catch (Exception e){
            e.printStackTrace();
        }

        return funks;
    }


    public static void main(String[] args) {
        ReadCSVFunkos rs = new ReadCSVFunkos();
        rs.readFileFunko();
    }
}
