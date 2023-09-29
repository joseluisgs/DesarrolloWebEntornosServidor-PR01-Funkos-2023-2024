package org.funko.models;

import java.util.UUID;
import java.util.Date;

public class Producto {
    private int id;
    private UUID cod;
    private String nombre;
    private String modelo;
    private double precio;
    private Date fechaLanzamiento;
    private Date createdAt;
    private Date updatedAt;

    public Producto() {
    }

    public Producto(int id, UUID cod, String nombre, String modelo, double precio, Date fechaLanzamiento, Date createdAt, Date updatedAt) {
        this.id = id;
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Date getFechaLanzamiento() {
        return fechaLanzamiento;
    }

    public void setFechaLanzamiento(Date fechaLanzamiento) {
        this.fechaLanzamiento = fechaLanzamiento;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", cod=" + cod + ", nombre=" + nombre + ", modelo=" + modelo + ", precio=" + precio
                + ", fechaLanzamiento=" + fechaLanzamiento + ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
    }
}
