package com.madirex.utils;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Esta clase contiene métodos para leer propiedades de un fichero
 */
public class ApplicationProperties {

    private static ApplicationProperties applicationPropertiesInstance;
    private final Properties properties;

    /**
     * Constructor
     * Lee el fichero de propiedades y lo carga en un objeto Properties
     * Si no se puede leer el fichero, se muestra un mensaje de error en el log
     */
    private ApplicationProperties() {
        properties = new Properties();
        try {
            properties.load(getClass().getClassLoader().getResourceAsStream("database.properties"));
        } catch (IOException ex) {
            Logger.getLogger(getClass().getName()).log(Level.ALL,ex,
                    () -> "IOException - Error al leer el fichero de propiedades.");
        }
    }

    /**
     * SINGLETON - Este método devuelve una instancia de la clase ApplicationProperties
     * @return Instancia de la clase ApplicationProperties
     */
    public static ApplicationProperties getInstance() {
        if (applicationPropertiesInstance == null) {
            applicationPropertiesInstance = new ApplicationProperties();
        }
        return applicationPropertiesInstance;
    }

    /**
     * Devuelve el valor de una clave del fichero de propiedades
     * @param keyName Nombre de la clave
     * @return Valor de la clave
     */
    public String readProperty(String keyName, String ifNotExistStr) {
        return properties.getProperty(keyName, ifNotExistStr);
    }
}