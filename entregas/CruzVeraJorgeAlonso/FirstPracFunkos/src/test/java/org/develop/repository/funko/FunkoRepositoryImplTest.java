package org.develop.repository.funko;

import org.develop.repository.FunkoRepository;
import org.develop.repository.FunkoRepositoryImpl;
import org.develop.excepciones.FunkoNotFoundException;
import org.develop.model.Funko;
import org.develop.model.Modelo;
import org.develop.services.database.DatabaseManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;


class FunkoRepositoryImplTest {
    private FunkoRepository funkoRepository;
    private Funko funko1,funko2;
    @BeforeEach
    void setup() throws SQLException {
        funkoRepository = FunkoRepositoryImpl.getInstance(DatabaseManager.getInstance());
        funkoRepository.deleteAll();
        funko1=new Funko();
        funko1.setUuid(UUID.randomUUID());
        funko1.setName("test");
        funko1.setModelo(Modelo.OTROS);
        funko1.setPrecio(1.0);
        funko1.setFecha_lanzamiento(LocalDate.now());
        funko2=new Funko();
        funko2.setUuid(UUID.randomUUID());
        funko2.setName("test2");
        funko2.setModelo(Modelo.MARVEL);
        funko2.setPrecio(1.5);
        funko2.setFecha_lanzamiento(LocalDate.now());
    }

    @AfterEach
    void teardown() throws SQLException {
        funkoRepository.deleteAll();
    }

    @Test
    void saveFunkoTest() throws SQLException {

        Funko funkoSaved = funkoRepository.save(funko1);

        assertAll(
                ()-> assertTrue(funkoSaved.getId()!=0),
                ()-> assertEquals(funko1.getUuid(),funkoSaved.getUuid()),
                ()-> assertEquals(funko1.getName(),funkoSaved.getName()),
                ()-> assertEquals(funko1.getModelo(),funkoSaved.getModelo()),
                ()-> assertEquals(funko1.getPrecio(),funkoSaved.getPrecio()),
                ()-> assertEquals(funko1.getFecha_lanzamiento(),funkoSaved.getFecha_lanzamiento())
        );
    }

     @Test
    void updateNotFunkoTest() throws SQLException {
        var idErr=20;

        funko2.setId(idErr);

         FunkoNotFoundException exception = assertThrows(FunkoNotFoundException.class,()->funkoRepository.update(funko2));


        assertAll(
                ()-> assertNotNull(exception),
                ()-> assertEquals("Funko con ID " + idErr +" no encontrado en la BD",exception.getMessage())
        );
    }
    @Test
    void updateFunkoTest() throws SQLException {

        var funkRepo = funkoRepository.save(funko1);

        funko2.setId(funkRepo.getId());
        funko2.setFecha_lanzamiento(LocalDate.of(2024,1,20));

        var funkoUpdt = funkoRepository.update(funko2);

        assertAll(
                ()-> assertNotNull(funkoUpdt),
                ()-> assertNotEquals(funko1.getUuid(),funkoUpdt.getUuid()),
                ()-> assertNotEquals(funko1.getName(),funkoUpdt.getName()),
                ()-> assertNotEquals(funko1.getModelo(),funkoUpdt.getModelo()),
                ()-> assertNotEquals(funko1.getPrecio(),funkoUpdt.getPrecio()),
                ()-> assertNotEquals(funko1.getFecha_lanzamiento(),funkoUpdt.getFecha_lanzamiento()),
                ()-> assertEquals(funko2.getUuid(),funkoUpdt.getUuid()),
                ()-> assertEquals(funko2.getName(),funkoUpdt.getName()),
                ()-> assertEquals(funko2.getModelo(),funkoUpdt.getModelo()),
                ()-> assertEquals(funko2.getPrecio(),funkoUpdt.getPrecio()),
                ()-> assertEquals(funko2.getFecha_lanzamiento(),funkoUpdt.getFecha_lanzamiento())
        );
    }

    @Test
    void findByIdTest() throws SQLException {

        funko1 = funkoRepository.save(funko1);

        var funkID = funkoRepository.findById(funko1.getId());

        assertAll(
                ()-> assertTrue(funkID.isPresent()),
                ()-> assertEquals(funko1.getId(),funkID.get().getId()),
                ()-> assertEquals(funko1,funkID.get())
        );
    }

    @Test
    void notFindByIdTest() throws SQLException {

        var idError=20;

        var funkID = funkoRepository.findById(idError);

        assertAll(
                ()-> assertFalse(funkID.isPresent())
        );
    }
    @Test
    void findAllTest() throws SQLException {
        funkoRepository.save(funko1);
        funkoRepository.save(funko2);
        var listFunkos = funkoRepository.findAll();

        assertAll(
                ()-> assertFalse(listFunkos.isEmpty()),
                ()-> assertTrue((listFunkos.size() == 2)),
                ()-> assertEquals(listFunkos.get(0),funko1),
                ()-> assertEquals(listFunkos.get(1),funko2)
        );
    }

    @Test
    void deleteByIdTest() throws SQLException {
        var funkSave = funkoRepository.save(funko1);
        var funkDel = funkoRepository.deleteById(funkSave.getId());
        var list = funkoRepository.findAll();
        assertAll(
                ()-> assertTrue(funkDel),
                ()-> assertEquals(0,list.size())
        );
    }

    @Test
    void deleteByIdTestError() throws SQLException{
        var idErr=20;
        var funkSave = funkoRepository.save(funko1);

        FunkoNotFoundException fknFe = assertThrows(FunkoNotFoundException.class,()->funkoRepository.deleteById(idErr));

        assertAll((
                ()-> assertEquals("Funko con ID " + idErr + " no encontrado en la BD",fknFe.getMessage())
                ));
    }

    @Test
    void deleteAllTest() throws SQLException {
        funkoRepository.save(funko1);
        funkoRepository.save(funko2);

        funkoRepository.deleteAll();
        var list = funkoRepository.findAll();

        assertAll(
                ()->assertTrue(list.isEmpty())
        );
    }


    @Test
    void findByNameTest() throws SQLException {

        funko1 = funkoRepository.save(funko1);
        funko2 = funkoRepository.save(funko2);

        var funkName = funkoRepository.findByName(funko1.getName());

        assertAll(
                ()-> assertFalse(funkName.isEmpty()),
                ()-> assertTrue(funkName.size() > 1),
                ()-> assertEquals(funko1,funkName.get(0))
        );
    }

    @Test
    void backupTest() throws SQLException {
        funkoRepository.save(funko1);
        funkoRepository.save(funko2);

        var back = funkoRepository.backup("funkosTest");

        assertTrue(back);
    }
}