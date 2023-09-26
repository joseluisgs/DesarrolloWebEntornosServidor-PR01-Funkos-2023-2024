package utils;

import java.util.Locale;

public class LocaleUtils {
    private LocaleUtils() {}

    public static final Locale getLocale() {
        return Locale.forLanguageTag("es-ES");
    }
}

