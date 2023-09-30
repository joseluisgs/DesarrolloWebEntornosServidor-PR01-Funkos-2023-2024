package com.madirex.exceptions;

public class FunkoNotFoundException extends FunkoException{
    public FunkoNotFoundException(String message) {
        super("Funko no encontrado: " + message);
    }
}
