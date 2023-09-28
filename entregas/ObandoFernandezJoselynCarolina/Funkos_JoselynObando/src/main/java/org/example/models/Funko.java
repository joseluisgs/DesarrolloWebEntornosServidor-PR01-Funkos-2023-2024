package org.example.models;



import com.sun.tools.javac.Main;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Getter
@Setter
@Builder
public class Funko {
    private UUID uuid;
    private int id;
    private String nombre;
    private Modelo modelo;
    private double precio;
    private LocalDate fecha_lanzamiento;

    public Funko(UUID uuid, String nombre, Modelo modelo, double precio, LocalDate fecha_lanzamiento) {
        this.uuid = uuid;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fecha_lanzamiento = fecha_lanzamiento;
    }

    public Funko() {
    }

    @Override
    public String toString() {
        return
                "uuid=" + uuid +
                        ", nombre='" + nombre + '\'' +
                        ", modelo=" + modelo +
                        ", precio=" + precio +
                        ", fecha_lanzamiento=" + fecha_lanzamiento
                ;
    }


}

