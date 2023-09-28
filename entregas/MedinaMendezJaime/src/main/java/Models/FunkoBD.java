package Models;
/*ID: autonumérico y clave primaria.
cod: UUID, no nulo, y se puede generar automáticamente un valor por defecto si no se le pasa.
nombre: cadena de caracteres de máximo 255.
modelo, solo puede ser MARVEL, DISNEY, ANIME u OTROS.
precio: un número real.
fecha_lanzamiento: es un tipo de fecha.
created_at: marca de tiempo que toma por valor si no se le pasa la fecha completa actual al crearse la entidad.
updated_at: marca de tiempo que toma por valor si no se le pasa la fecha completa al crearse la entidad o actualizarse.*/

import enums.Modelo;
import lombok.Getter;

import java.time.LocalDate;
import java.util.UUID;

@Getter
public class FunkoBD {
    private final UUID id;
    private final UUID cod;
    private final String nombre;
    private final Modelo modelo;
    private final double precio;
    private final LocalDate fechaLanzamiento;
    private final LocalDate created_at;
    private LocalDate updated_at;

    public FunkoBD(UUID id, UUID cod, String nombre, Modelo modelo, double precio, LocalDate fechaLanzamiento) {
        this.id = id;
        this.cod = cod;
        this.nombre = nombre;
        this.modelo = modelo;
        this.precio = precio;
        this.fechaLanzamiento = fechaLanzamiento;
        this.created_at = LocalDate.now();
        this.updated_at = LocalDate.now();
    }

    public void setUpdated_at(LocalDate updated_at) {
        this.updated_at = updated_at;
    }
}
