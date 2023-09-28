package org.develop.services.funko;

import org.develop.excepciones.FunkoNotFoundException;
import org.develop.excepciones.FunkoNotSaveException;
import org.develop.model.Funko;
import org.develop.repository.FunkoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FunkoServiceImpl implements FunkoService {

    private static final int CACHE_SIZE =25;

    private static FunkoServiceImpl instance;
    private final Map<Integer,Funko> cache;
    private final Logger logger = LoggerFactory.getLogger(FunkoServiceImpl.class);
    private final FunkoRepository funkoRepository;


    private FunkoServiceImpl(FunkoRepository funkoRepository) {
        this.funkoRepository = funkoRepository;
        this.cache=new LinkedHashMap<>(CACHE_SIZE, 0.75f, true){
            @Override
            protected boolean removeEldestEntry(Map.Entry<Integer, Funko> eldest) {
                return size() > CACHE_SIZE;
            }

        };
    }
    
    public static FunkoServiceImpl getInstance(FunkoRepository funkoRepository){
            if (instance==null){
                instance=new FunkoServiceImpl(funkoRepository);
            }

            return instance;
    }

    @Override
    public Funko save(Funko funko) throws SQLException, FunkoNotSaveException {
        logger.debug("Guardando Funko.......");
        funko = funkoRepository.save(funko);
        cache.put(funko.getId(),funko);
        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException, FunkoNotFoundException {
        logger.debug("Actualizando Funko......");
        funko = funkoRepository.update(funko);
        cache.put(funko.getId(),funko);
        return funko;
    }

    @Override
    public Optional<Funko> findById(Integer id) throws SQLException {
        logger.debug("Obteniedo Funko con ID: " + id);
        Funko funk = cache.get(id);
        if (funk != null){
            logger.debug("Funko encontrado en la CACHE");
            return Optional.of(funk);
        }else{
            logger.debug("Funko no encontrado en la CACHE - Buscando en la BD");
            var funkOpt = funkoRepository.findById(id);
            funkOpt.ifPresent(fv -> cache.put(id,fv));
            return funkOpt;
        }
    }

    @Override
    public List<Funko> findAll() throws SQLException {
        logger.debug("Obteniendo todos los Funkos");
        return funkoRepository.findAll();
    }

    @Override
    public List<Funko> findByName(String name) throws SQLException {
        logger.debug("Obteniendo Funkos Coincidentes con: " + name);
        return funkoRepository.findByName(name);
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {
        logger.debug("Eliminando Funko con id: " + id);
        boolean del = funkoRepository.deleteById(id);

        if (del){
            cache.remove(id);
        }
        return del;
    }

    @Override
    public void deleteAll() throws SQLException {
        logger.debug("Eliminando todos los Funkos de la BD");
        funkoRepository.deleteAll();

        cache.clear();
    }

    @Override
    public boolean backup(String name) throws SQLException {
        return funkoRepository.backup(name);
    }
}
