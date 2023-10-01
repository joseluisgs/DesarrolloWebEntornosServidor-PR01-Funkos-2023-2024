package dev.services.db;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.adapters.LocalDateAdapter;
import dev.models.Funko;
import dev.repositories.FunkosRepoImpl;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;


public class FunkoDBImpl implements FunkoDB {

    private static final int CACHE_SIZE = 25;

    private final Map<UUID, Funko> cache;

    private final FunkosRepoImpl repository;

    private static FunkoDBImpl instance;

    private final Logger logger = LoggerFactory.getLogger(FunkoDBImpl.class);


    private FunkoDBImpl(FunkosRepoImpl repository) {
        this.repository = repository;
        this.cache = new LinkedHashMap<>(CACHE_SIZE, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<UUID, Funko> eldest) {
                return size() > CACHE_SIZE;
            }
        };
    }

    public static FunkoDBImpl getInstance(FunkosRepoImpl funkRepo){

        if (instance == null){
            instance = new FunkoDBImpl(funkRepo);
        }

        return instance;

    }

    @Override
    public List<Funko> findAll() throws SQLException, IOException {
        logger.debug("Obteniendo todos los funkos");
        return repository.findAll();
    }

    @Override
    public Optional<Funko> findById(UUID id) throws SQLException, IOException {
        logger.debug("obteniendo funko con id: "+id);
        if (cache.containsKey(id)){
            logger.debug("Funko encontrado en cache");

            return Optional.of(cache.get(id));
        }else{

            logger.debug("Funko no encontrado en cache, buscando en BD");

            Optional<Funko> funkoRes = repository.findById(id);

            if (funkoRes.isPresent()){

                cache.put(id, funkoRes.get());

                return funkoRes;
            }

        }

        return Optional.empty();

    }

    @Override
    public Funko save(Funko entity) throws SQLException, IOException {

        logger.debug("Guardando Funko con id "+entity.getCodigo());

        entity = repository.save(entity);

        cache.put(entity.getCodigo(), entity);

        return entity;

    }

    @Override
    public Funko update(UUID id, Funko entity) throws SQLException, IOException {

        logger.debug("Actualizando Funko con id "+entity.getCodigo());

        entity = repository.update(id, entity);

        cache.put(entity.getCodigo(), entity);

        return entity;

    }

    @Override
    public boolean delete(UUID id) throws SQLException, IOException {

        logger.debug("Eliminando Funko con id "+id);

        boolean isDeleted = repository.delete(id);

        if (isDeleted){

            cache.remove(id);

        }

        return isDeleted;

    }

    @Override
    public Optional<Funko> findByName(String name) throws SQLException, IOException {


        logger.debug("Buscando Funko con nombre "+name);

        Optional<Funko> funkoRes = repository.findByName(name);

        if (funkoRes.isPresent()){

            cache.put(funkoRes.get().getCodigo(), funkoRes.get());

            return funkoRes;

        }

        return Optional.empty();

    }

    public boolean backup(String path) throws IOException, SQLException {

        logger.debug("Realizando backup de la BD");

        Gson gson = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

        List<Funko> funkos = this.findAll();

        String json = gson.toJson(funkos);

        Files.writeString(new File(path).toPath(), json);

        return true;

    }


}
