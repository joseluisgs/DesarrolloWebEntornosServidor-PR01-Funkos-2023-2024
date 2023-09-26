package exceptions;

import models.Funko;

public class FunkoNoEncotradoException extends FunkoException {
    public FunkoNoEncotradoException(String message) {
        super(message);
    }
}