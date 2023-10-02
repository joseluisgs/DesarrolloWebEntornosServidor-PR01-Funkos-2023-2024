package dev.locale;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public class EspanaLocale {

    private static final Locale localeEs = new Locale("es", "ES");

    public static String toLocalDate(LocalDate date) {
        return date.format(
                DateTimeFormatter
                        .ofLocalizedDate(FormatStyle.MEDIUM).withLocale(localeEs)
        );
    }

    public static String toLocalDateTime(LocalDateTime dateTime) {
        return dateTime.format(
                DateTimeFormatter
                        .ofLocalizedDateTime(FormatStyle.MEDIUM).withLocale(localeEs)
        );
    }

    public static String toLocalMoney(double money) {
        return NumberFormat.getCurrencyInstance(localeEs).format(money);
    }

    public static String toLocalNumber(double number) {
        return NumberFormat.getNumberInstance(localeEs).format(number);
    }

}
