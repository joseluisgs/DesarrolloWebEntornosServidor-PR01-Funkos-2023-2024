package com.madirex.exceptions;

public class CreateFolderException extends Exception{
    public CreateFolderException(String message) {
        super("Error al crear la carpeta: " + message);
    }
}
