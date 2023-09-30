package exercise.models;

import java.util.UUID;

public class Funko {
    private String id;
    private UUID cod;
    private String nombre;
    private String modelo;
    private double precio;
    private String fechaLanzamiento;
    private String created_at;
    private String updated_at;

    public Funko(String id, UUID cod, String nombre, String modelo, double precio, String fechaLanzamiento, String created_at, String updated_at) {
        this.id = id;
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public Long getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UUID getCod() {
        return cod;
    }

    public void setCod(UUID cod) {
        this.cod = cod;
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

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public String getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(String fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}

