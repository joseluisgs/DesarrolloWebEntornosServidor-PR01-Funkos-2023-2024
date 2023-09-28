package com.madirex.exceptions;

public class FunkoNotValidException extends FunkoException{
    public FunkoNotValidException(String message) {
        super("Funko no válido: " + message);
    }
}
