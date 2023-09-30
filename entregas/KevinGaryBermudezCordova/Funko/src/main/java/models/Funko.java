package models;

import com.google.gson.annotations.Expose;
import lombok.Data;

import java.time.LocalDate;

@Data
public class Funko {
    @Expose
    private final String cod;
    @Expose
    private final String nombre;
    @Expose
    private final String modelo;
    @Expose
    private double precio;
    @Expose
    private final LocalDate fecha;
    public Funko(String cod, String nombre, String modelo, Double precio, LocalDate fecha) {
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fecha = fecha;
    }
}
