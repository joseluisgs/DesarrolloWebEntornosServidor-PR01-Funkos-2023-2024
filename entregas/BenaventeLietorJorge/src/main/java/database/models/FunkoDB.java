package database.models;

import lombok.*;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class FunkoDB extends Funko {
    private int id;
    private final LocalDateTime createdDate;
    private final LocalDateTime updatedDate;

    @Override
    public String toString() {
        Locale locale = new Locale("es", "ES");
        String uuid = getCod().toString();
        String nombre = getNombre();
        String modelo = getModelo().toString();
        double precio = getPrecio();
        LocalDate fechaLanzamiento = getFechaLanzamiento();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(locale);
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
