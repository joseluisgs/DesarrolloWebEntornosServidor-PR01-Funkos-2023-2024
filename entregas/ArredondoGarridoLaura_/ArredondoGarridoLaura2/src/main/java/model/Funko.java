package model;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public class Funko {
private UUID COD;
private String nombre;
private String modelo;
private double precio;
private LocalDate FechaLanzamiento;
private LocalDateTime created_at;

private LocalDateTime update_at;

public Funko(){}

    public UUID getCOD() {
        return COD;
    }

    public void setCOD(UUID COD) {
        this.COD = COD;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public double getPrecio() {
        return precio;
    }

    public LocalDate getFechaLanzamiento() {
        return FechaLanzamiento;
    }

    public void setFechaLanzamiento(LocalDate fechaLanzamiento) {
        FechaLanzamiento = fechaLanzamiento;
    }

    public Object getCod() {
        return null;
    }

    public void setId(int anInt) {
    }

    public int getId() {
        return 0;
    }

    public void setCod(UUID cod) {
    }
}
