package models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@ToString
public class Funko {

    private final UUID id;
    private final UUID cod;
    private final String nombre;
    private final Modelo modelo;
    private final double precio;
    private final LocalDate fecha_lanzamiento;
    private final LocalDate created_at;
    private final LocalDate updated_at;

    public Funko(UUID id, UUID cod, String nombre, Modelo modelo, double precio, LocalDate fecha_lanzamiento) {
        this.id = UUID.randomUUID();
        this.cod = UUID.randomUUID();
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fecha_lanzamiento = fecha_lanzamiento;
        this.created_at = LocalDate.now();
        this.updated_at = LocalDate.now();
    }



}
