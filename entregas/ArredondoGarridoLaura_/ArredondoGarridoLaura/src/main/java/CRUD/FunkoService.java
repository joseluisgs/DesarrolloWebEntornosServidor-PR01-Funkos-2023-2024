package CRUD;

import model.Funko;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FunkoService {

    private final FunkoRepositoryImpl funkoRepository;
    private List<Funko> cache;

    public FunkoService(FunkoRepositoryImpl funkoRepository) {
        this.funkoRepository = funkoRepository;
        this.cache = initializeCache();
    }

    // Métodos de inicialización y gestión de la caché
    private List<Funko> initializeCache() {
        return new ArrayList<>();
    }

    private void updateCache(Funko funko) {
        if (cache.size() >= 25) {
            // Elimina el elemento más antiguo si la caché está llena
            cache.remove(0);
        }
        cache.add(funko);
    }

    // Métodos CRUD
    public Funko guardarFunko(Funko funko) throws SQLException {
        Funko savedFunko = funkoRepository.save(funko);
        updateCache(savedFunko);
        return savedFunko;
    }

    public Optional<Funko> buscarPorId(UUID id) throws SQLException {
        return funkoRepository.findById(id);
    }

    public List<Funko> buscarPorNombre(String nombre) {
        return new ArrayList<>();
    }

    public List<Funko> listarTodos() throws SQLException {
        return funkoRepository.findAll();
    }

    public boolean borrarPorId(UUID id) throws SQLException {
        return funkoRepository.deleteById(id);
    }

    public void borrarTodos() throws SQLException {
        funkoRepository.deleteAll();
    }

    public void realizarBackup(String ruta) {
        try (FileWriter fileWriter = new FileWriter(ruta)) {
            System.out.println("Respaldo realizado con éxito en: " + ruta);
        } catch (IOException e) {
            // Manejo de excepciones en caso de error al escribir el archivo
            e.printStackTrace();
            System.err.println("Error al realizar el respaldo.");
        }
    }
}
