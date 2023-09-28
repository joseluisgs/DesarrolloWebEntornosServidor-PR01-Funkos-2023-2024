package dev.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.Locale;
import java.util.UUID;



@Data
@AllArgsConstructor
public class Funko {

    private UUID codigo;

    private String nombre;

    private Modelo modelo;

    private double precio;

    private LocalDate fechaLanzamiento;



    @Override
    public String toString() {
        Locale locale = Locale.forLanguageTag("es-ES");

        return String.format(locale, "Código: %s\nNombre: %s\nModelo: %s\nPrecio: %.2f€\nFecha de lanzamiento: %s\n",
                codigo, nombre, modelo, precio, fechaLanzamiento);
    }


}


