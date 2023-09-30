package org.funkos.models;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import org.funkos.enums.Modelo;
import org.funkos.locale.MyLocale;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Getter
public class Funko {
    private Integer id;
    private UUID COD;
    private String nombre;
    private Modelo modelo;
    private double precio;
    private LocalDate fecha;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;

    @Override
    public String toString() {
        return "Funko{" +
                "id=" + id +
                ", COD=" + COD +
                ", nombre='" + nombre + '\'' +
                ", modelo=" + modelo +
                ", precio=" + MyLocale.toLocalMoney(precio) +
                ", fecha=" + MyLocale.toLocalDate(fecha) +
                ", created_at=" + MyLocale.toLocalDateTime(created_at) +
                ", updated_at=" + MyLocale.toLocalDateTime(updated_at) +
                '}';
    }
}
