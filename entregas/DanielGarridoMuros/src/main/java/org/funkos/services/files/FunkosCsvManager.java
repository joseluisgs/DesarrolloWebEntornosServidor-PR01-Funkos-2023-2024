package org.funkos.services.files;

import org.funkos.enums.Modelo;
import org.funkos.models.Funko;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FunkosCsvManager {

    private final List<Funko> funkos = new ArrayList<>();

    public List<Funko> readAllCSV(String route_file){
        try {
            List<String> lines =  Files.newBufferedReader(Paths.get(route_file), StandardCharsets.UTF_8 ).lines().toList();
            for(int i = 1; i < lines.size(); i++){
                String[] lines_split = lines.get(i).split(",");
                DecimalFormat df = new DecimalFormat("#.###");
                Funko funko = Funko.builder()
                        .COD(UUID.fromString(lines_split[0].substring(0, 35)))
                        .nombre(lines_split[1])
                        .modelo(Modelo.valueOf(lines_split[2]))
                        .precio(Double.parseDouble(lines_split[3].replace(',', '.')))
                        .fecha(LocalDate.parse(lines_split[4]))
                        .created_at(LocalDateTime.now())
                        .updated_at(LocalDateTime.now())
                        .build();

                funkos.add(funko);
            }
            return funkos;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
