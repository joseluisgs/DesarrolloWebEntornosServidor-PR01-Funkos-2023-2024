package org.models;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Setter
@Getter
@Builder
public class Funko {
    private long id;
    private UUID COD;
    private String nombre;
    private Modelo modelo;
    private double precio;
    private LocalDate fechaLanzamiento;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
