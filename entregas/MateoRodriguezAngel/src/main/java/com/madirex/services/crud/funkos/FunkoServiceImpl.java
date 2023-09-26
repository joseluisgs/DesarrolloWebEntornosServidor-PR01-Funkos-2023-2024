package com.madirex.services.crud.funkos;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.madirex.exceptions.FunkoException;
import com.madirex.models.Funko;
import com.madirex.repositories.FunkoRepository;
import com.madirex.utils.LocalDateAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FunkoServiceImpl implements FunkoService {
    private static final int CACHE_SIZE = 25;
    private static FunkoServiceImpl instance;
    private final Map<String, Funko> cache;
    private final Logger logger = LoggerFactory.getLogger(FunkoServiceImpl.class);
    private final FunkoRepository funkoRepository;

    private FunkoServiceImpl(FunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
        this.cache = new LinkedHashMap<>(CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<String, Funko> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }

    public static FunkoServiceImpl getInstance(FunkoRepository funkoRepository) {
        if (instance == null) {
            instance = new FunkoServiceImpl(funkoRepository);
        }
        return instance;
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        logger.debug("Obteniendo todos los funkos");
        return funkoRepository.findAll();
    }

    @Override
    public List<Funko> findByName(String name) throws SQLException {
        logger.debug("Obteniendo todos los funkos ordenados por nombre");
        return funkoRepository.findByName(name);
    }

    @Override
    public void backup(String path, String fileName) {
        File dataDir = new File(path);
        if (dataDir.exists()) {
            dataDir.mkdir();
            String dest = path + fileName;
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                    .setPrettyPrinting()
                    .create();
            String json = null;
            try {
                json = gson.toJson(findAll());
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            try {
                Files.writeString(new File(dest).toPath(), json);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }else{
            //TODO: mensaje de error de no existe
        }
    }

    @Override
    public Optional<Funko> findById(String id) throws SQLException {
        logger.debug("Obteniendo funko por id");
        Funko funko = cache.get(id);
        if (funko != null) {
            logger.debug("Funko encontrado en caché");
            return Optional.of(funko);
        } else {
            logger.debug("Funko no encontrado en caché, buscando en base de datos");
            var optional = funkoRepository.findById(id);
            optional.ifPresent(value -> cache.put(id, value));
            return optional;
        }
    }

    @Override
    public Optional<Funko> save(Funko funko) throws SQLException, FunkoException {
        Optional<Funko> modified;
        logger.debug("Guardando funko");
        modified = funkoRepository.save(funko);
        cache.put(funko.getCod().toString(), funko);
        return modified;
    }

    @Override
    public Optional<Funko> update(String funkoId, Funko newFunko) throws SQLException, FunkoException {
        Optional<Funko> modified;
        logger.debug("Actualizando funko");
        modified = funkoRepository.update(funkoId, newFunko);
        cache.put(newFunko.getCod().toString(), newFunko);
        return modified;
    }
}