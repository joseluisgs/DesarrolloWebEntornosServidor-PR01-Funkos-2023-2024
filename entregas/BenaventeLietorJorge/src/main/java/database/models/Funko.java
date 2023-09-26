package database.models;

import enums.Modelo;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Funko {
    private final UUID cod;
    private String nombre;
    private final Modelo modelo;
    private final double precio;
    private LocalDate fechaLanzamiento;


    public static Funko fromCsv(String[] data) {
        return new Funko(UUID.fromString(data[0].substring(0, 35)), data[1], Modelo.valueOf(data[2]),Double.parseDouble(data[3]), LocalDate.parse(data[4]));
    }


}
