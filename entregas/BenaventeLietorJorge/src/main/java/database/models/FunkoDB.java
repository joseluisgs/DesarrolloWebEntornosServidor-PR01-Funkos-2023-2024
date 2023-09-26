package database.models;

import enums.Modelo;
import lombok.*;
import utils.LocaleUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;
import java.util.UUID;


@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
public class FunkoDB extends Funko {
    private int id;
    private final LocalDateTime createdDate;
    private LocalDateTime updatedDate;


    public FunkoDB(UUID cod, String nombre, Modelo modelo, double precio, LocalDate fechaLanzamiento, int id, LocalDateTime createdDate, LocalDateTime updatedDate) {
        super(cod, nombre, modelo, precio, fechaLanzamiento);
        this.id = id;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
    }

    public static FunkoDB fromResultSet(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String cod = resultSet.getString("cod");
        String nombre = resultSet.getString("nombre");
        String modelo = resultSet.getString("modelo");
        double precio = resultSet.getDouble("precio");
        LocalDate fechaLanzamiento = resultSet.getDate("fecha_lanzamiento").toLocalDate();
        LocalDateTime createdDate = resultSet.getTimestamp("created_at").toLocalDateTime();
        LocalDateTime updatedDate = resultSet.getTimestamp("updated_at").toLocalDateTime();
        return new FunkoDB(UUID.fromString(cod), nombre, Modelo.valueOf(modelo), precio, fechaLanzamiento, Integer.parseInt(id), createdDate, updatedDate);
    }

    @Override
    public String toString() {
        Locale locale = LocaleUtils.getLocale();
        String uuid = getCod().toString();
        String nombre = getNombre();
        String modelo = getModelo().toString();
        double precio = getPrecio();
        LocalDate fechaLanzamiento = getFechaLanzamiento();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(locale);
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String createdDate = dateTimeFormatter.format(this.createdDate);
        String updatedDate = dateTimeFormatter.format(this.updatedDate);
        String launchDate = dateTimeFormatter.format(fechaLanzamiento);
        String price = currencyFormatter.format(precio);
        return "FunkoDB{" +
                "id=" + id +
                ", cod=" + uuid +
                ", nombre='" + nombre + '\'' +
                ", modelo=" + modelo +
                ", precio=" + price +
                ", fechaLanzamiento=" + launchDate +
                ", createdDate=" + createdDate +
                ", updatedDate=" + updatedDate +
                "}";
    }
}
