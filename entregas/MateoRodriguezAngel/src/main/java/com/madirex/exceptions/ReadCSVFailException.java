package com.madirex.exceptions;

public class ReadCSVFailException extends Exception{
    public ReadCSVFailException(String message) {
        super("Error al leer el archivo CSV: " + message);
    }
}
