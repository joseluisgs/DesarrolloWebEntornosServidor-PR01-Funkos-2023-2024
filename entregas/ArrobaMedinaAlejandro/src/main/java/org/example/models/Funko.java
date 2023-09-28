package org.example.models;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class Funko {
    private final UUID codigo;
    private final String Nombre;
    private enum Modelo {MARVEL, DISNEY, ANIME, OTROS};
    private final double Precio;
    private final LocalDate FechLanzamiento;

    public Funko(UUID codigo, String nombre, double precio, LocalDate fechLanzamiento) {
        this.codigo = codigo;
        Nombre = nombre;
        Precio = precio;
        FechLanzamiento = fechLanzamiento;
    }

    public UUID getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public double getPrecio() {
        return Precio;
    }

    public LocalDate getFechLanzamiento() {
        return FechLanzamiento;
    }

    @Override
    public String toString() {
        return "Funko{" +
                "codigo=" + codigo +
                ", Nombre='" + Nombre + '\'' +
                ", Precio=" + Precio +
                ", FechLanzamiento=" + FechLanzamiento +
                '}';
    }
}
