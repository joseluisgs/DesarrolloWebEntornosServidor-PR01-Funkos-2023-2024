package org.example.models;

import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
public class Funkos {
    private Long id;
    private UUID cod;
    private String nombre;
    private String modelo;
    private Double precio;
    private Date fechaLanzamiento;
    private Date createdAt;
    private Date updatedAt;
}
