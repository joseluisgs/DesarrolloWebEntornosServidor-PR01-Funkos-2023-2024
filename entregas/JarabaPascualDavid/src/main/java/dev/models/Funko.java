package dev.models;

import dev.locale.EspanaLocale;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;



@Data
@AllArgsConstructor
@Builder
public class Funko {

    private UUID codigo;

    private String nombre;

    private Modelo modelo;

    private double precio;

    private LocalDate fechaLanzamiento;




    @Override
    public String toString() {

        return String.format("Código: %s\nNombre: %s\nModelo: %s\nPrecio: %s\nFecha de lanzamiento: %s\n",
                codigo, nombre, modelo, EspanaLocale.toLocalMoney(precio), EspanaLocale.toLocalDate(fechaLanzamiento));

    }


}


