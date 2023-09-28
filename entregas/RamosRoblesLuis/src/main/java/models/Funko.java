package models;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;
@Data
@Builder
public class Funko {

    private final UUID cod;
    private final String nombre;
    private final String modelo;
    private final double precio;
    private final LocalDate fechaLanzamiento;


    @Override
    public String toString() {
        return "Funko{" +
                "cod=" + cod +
                ", nombre='" + nombre + '\'' +
                ", modelo='" + modelo + '\'' +
                ", precio=" + precio +
                ", fechaLanzamiento=" + fechaLanzamiento +
                '}';
    }
}
