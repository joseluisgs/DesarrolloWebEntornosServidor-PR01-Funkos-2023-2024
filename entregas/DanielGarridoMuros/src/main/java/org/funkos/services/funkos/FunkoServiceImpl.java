package org.funkos.services.funkos;

import org.funkos.cache.funko.FunkoCacheImpl;
import org.funkos.models.Funko;
import org.funkos.repositories.funko.FunkoRepositoryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class FunkoServiceImpl implements FunkoService{


    private static FunkoServiceImpl instance;

    private FunkoRepositoryImpl funkoRepository;

    private FunkoCacheImpl CACHE;

    private static Logger logger = LoggerFactory.getLogger(FunkoServiceImpl.class);



    private FunkoServiceImpl(FunkoRepositoryImpl funkoRepository, int cacheSize){
        this.CACHE = new FunkoCacheImpl(cacheSize);
        this.funkoRepository = funkoRepository;
    }

    public static FunkoServiceImpl getInstance(FunkoRepositoryImpl funkoRepository, int cacheSize){
        if(instance == null){
            instance = new FunkoServiceImpl(funkoRepository, cacheSize);
        }
        return instance;
    }

    @Override
    public Funko save(Funko funko) throws SQLException, SQLException {
        funko = funkoRepository.save(funko);
        if(funko.getId() != null){
            logger.debug("Añadiendo funko {} a la cache", funko.getCOD());
            CACHE.put(funko.getId(), funko);
        }else{
            logger.error("El funko {} no se puedo añadir a la cache", funko.getCOD());
        }

        return funko;
    }

    @Override
    public Funko update(Funko funko) throws SQLException, SQLException {
        funko = funkoRepository.update(funko);
        if(funko != null){
            logger.debug("El funko {} se pudo añadir a la cache", funko.getCOD());
            CACHE.put(funko.getId(), funko);
        }else{
            logger.error("El funko {} no se pudo añadir a la cache", funko.getCOD());
        }

        return funko;
    }

    @Override
    public Optional<Funko> findById(Integer id) throws SQLException {
        Funko funko = CACHE.get(id);
        if(funko != null){
            logger.debug("Obteniendo funko {} desde la cache", funko.getCOD());
            return Optional.of(funko);
        }else{
            logger.error("Obteniendo funko de la base de datos");
            return funkoRepository.findById(id);
        }

    }

    @Override
    public List<Funko> findAll() throws SQLException {
        return funkoRepository.findAll();
    }

    @Override
    public boolean deleteById(Integer id) throws SQLException {

        boolean isDeleted = funkoRepository.deleteById(id);
        if(isDeleted){
            logger.debug("El funko con id: {} se borro de la cache", id);
            CACHE.remove(id);
        }else{
            logger.error("El funko con id: {} no se borro");
        }
        return isDeleted;
    }

    @Override
    public void deleteAll() throws SQLException {
        funkoRepository.deleteAll();

        CACHE.clear();
    }

    @Override
    public Optional<Funko> findByNombre(String nombre) throws SQLException {
        Funko funko = funkoRepository.findByNombre(nombre).get();

        if(funko != null){
            logger.debug("El funko con nombre {} se guardo en la cache", nombre);
            CACHE.put(funko.getId(), funko);
        }else{
            logger.error("El funko no se encontro");
        }


        return Optional.of(funko);
    }

    public void backup() throws SQLException, IOException {
        funkoRepository.backup();
    }

    public void restore() throws SQLException, IOException {
        funkoRepository.restore();
    }
}
