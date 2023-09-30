package com.madirex.validators;

import com.madirex.exceptions.FunkoNotValidException;
import com.madirex.models.Funko;

public class FunkoValidator {
    public static void validate(Funko funko) throws FunkoNotValidException {
        if (funko.getName().isEmpty()) {
            throw new FunkoNotValidException("El nombre no puede estar vacío");
        }
        if (funko.getPrice() < 0) {
            throw new FunkoNotValidException("El precio no puede ser menor a 0");
        }

        if (funko.getReleaseDate() == null) {
            throw new FunkoNotValidException("Tiene que tener una fecha de lanzamiento"); //TODO: TEST
        }
        if (funko.getModel() == null) {
            throw new FunkoNotValidException("Tiene que tener un modelo asignado"); //TODO: TEST
        }
    }
}
