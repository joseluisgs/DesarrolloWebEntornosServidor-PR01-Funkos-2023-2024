package com.madirex.exceptions;

public class FunkoExceptionNotSaved extends FunkoException{
    public FunkoExceptionNotSaved(String message) {
        super("Funko no guardado: " + message);
    }
}
