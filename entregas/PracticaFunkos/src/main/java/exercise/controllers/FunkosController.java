package exercise.controllers;

import exercise.models.Funko;
import exercise.repositories.funkos.FunkosRepository;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class FunkosController {
    private final FunkosRepository funkosRepository;

    public FunkosController(FunkosRepository funkosRepository) {
        this.funkosRepository = funkosRepository;
    }

    public Funko saveFunko(Funko funko) {
        try {
            return funkosRepository.save(funko);
        } catch (SQLException e) {
            System.err.println("Error al guardar el funko: " + e.getMessage());
        }
        return null;
    }

    public void deleteAll() {
        try {
            funkosRepository.deleteAll();
        } catch (SQLException e) {
            System.err.println("Error al borrar todos los funkos: " + e.getMessage());
        }
    }

    public List<Funko> findAll() {
        try {
            return funkosRepository.findAll();
        } catch (SQLException e) {
            System.err.println("Error al buscar todos los funkos: " + e.getMessage());
        }
        return null;
    }

    public Funko update(Funko funko) {
        try {
            return funkosRepository.update(funko.getId(), funko);
        } catch (SQLException e) {
            System.err.println("Error al actualizar el funko: " + e.getMessage());
        }
        return null;
    }

    public Funko findById(int id) {
        try {
            return funkosRepository.findById(id).orElseThrow(() -> new RuntimeException("No existe un funko con la id" + id));
        } catch (SQLException e) {
            System.err.println("Error al buscar el funko por ID: " + e.getMessage());
        }
        return null;
    }

    public Funko findByName(String name) {
        try {
            return funkosRepository.findByName(name).orElseThrow(() -> new RuntimeException("No existe un funko con el nombre " + name));
        } catch (SQLException e) {
            System.err.println("Error al buscar el funko por nombre: " + e.getMessage());
        }
        return null;
    }

    public Funko delete(Funko funko) {
        try {
            return funkosRepository.delete(funko.getId());
        } catch (SQLException e) {
            System.err.println("Error al borrar el funko: " + e.getMessage());
        }
        return null;
    }

    public void backup() {
        try {
            funkosRepository.backup();
        } catch (IOException | SQLException e) {
            System.err.println("Error al hacer backup: " + e.getMessage());
        }
    }

    public void restore() {
        try {
            funkosRepository.restore();
        } catch (IOException | SQLException e) {
            System.err.println("Error al hacer restore: " + e.getMessage());
        }
    }
}
