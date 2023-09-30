package exercise.services.funkos;

import exercise.exceptions.funkos.FunkoException;
import exercise.models.Funko;
import exercise.repositories.funkos.FunkosRepository;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FunkosServiceImpl implements FunkosService {
    // Para mi cache
    private static final int CACHE_SIZE = 10; // Tamaño de la cache
    // Singleton
    private static FunkosServiceImpl instance;
    private final Map<Long, Funko> cache; // Nuestra cache
    private final FunkosRepository funkosRepository;

    private FunkosServiceImpl(FunkosRepository funkosRepository) {
        this.funkosRepository = funkosRepository;
        // Inicializamos la cache con el tamaño y la política de borrado de la misma
        // borramos el más antiguo cuando llegamos al tamaño máximo
        this.cache = new LinkedHashMap<>(CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<Long, Funko> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }


    public static FunkosServiceImpl getInstance(FunkosRepository funkosRepository) {
        if (instance == null) {
            instance = new FunkosServiceImpl(funkosRepository);
        }
        return instance;
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        logger.debug("Obteniendo todos los Funkos");
        // No vamos a cachear todos los Funkos, pueden ser muchos
        return FunkosRepository.findAll();
    }

    @Override
    public List<Funko> findAllByNombre(String nombre) throws SQLException {
        logger.debug("Obteniendo todos los Funkos ordenados por nombre");
        // No vamos a cachear todos los Funkos, pueden ser muchos
        return FunkosRepository.findByNombre(nombre);
    }

    @Override
    public Optional<Funko> findById(long id) throws SQLException {
        logger.debug("Obteniendo Funko por id");
        // Buscamos en la cache
        Funko Funko = cache.get(id);
        if (Funko != null) {
            logger.debug("Funko encontrado en cache");
            return Optional.of(Funko);
        } else {
            // Buscamos en la base de datos
            logger.debug("Funko no encontrado en cache, buscando en base de datos");
            var optional = FunkosRepository.findById(id);
            optional.ifPresent(value -> cache.put(id, value));
            return optional;
        }
    }

    @Override
    public Funko save(Funko Funko) throws SQLException, FunkoException {
        logger.debug("Guardando Funko");
        // Guardamos en la base de datos
        Funko = FunkosRepository.save(Funko);
        // Guardamos en la cache
        cache.put(Funko.getId(), Funko);
        return Funko;
    }

    @Override
    public Funko update(Funko Funko) throws SQLException, FunkoException {
        logger.debug("Actualizando Funko");
        // Actualizamos en la base de datos
        Funko = FunkosRepository.update(Funko);
        // Actualizamos en la cache
        cache.put(Funko.getId(), Funko);
        return Funko;
    }


    @Override
    public void deleteAll() throws SQLException {
        logger.debug("Borrando todos los Funkos");
        // Borramos en la base de datos
        FunkosRepository.deleteAll();
        // Borramos en la cache
        cache.clear();
    }
}
