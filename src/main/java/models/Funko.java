package models;

import lombok.Builder;
import lombok.Data;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
@Data
@Builder
public class Funko {
    private UUID cod;
    private String nombre;
    private String modelo;
    private Double precio;
    private LocalDateTime fecha_lanzamiento;

    public Funko(UUID cod,String nombre,String modelo,Double precio,LocalDateTime fecha_lanzamiento) {
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fecha_lanzamiento = fecha_lanzamiento;
    }


}
