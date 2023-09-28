package org.develop.services.funko;

import org.develop.repository.FunkoRepository;
import org.develop.excepciones.FunkoNotFoundException;
import org.develop.model.Funko;
import org.develop.model.Modelo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunkoServiceImplTest {

    private Funko funko1, funko2;
    @Mock
    FunkoRepository repository;

    @InjectMocks
    FunkoServiceImpl service;

    @BeforeEach
    void setup(){
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



    @Test
    void saveTest() throws SQLException {
        when(repository.save(funko1)).thenReturn(funko1);

        var res = repository.save(funko1);

        assertAll(
                ()-> assertEquals(res.getUuid(),funko1.getUuid()),
                ()-> assertEquals(res.getName(),funko1.getName()),
                ()-> assertEquals(res.getModelo(),funko1.getModelo()),
                ()-> assertEquals(res.getPrecio(),funko1.getPrecio()),
                ()-> assertEquals(res.getFecha_lanzamiento(),funko1.getFecha_lanzamiento())
        );
    }

    @Test
    void updateTest() throws SQLException {
        funko2.setId(1);

        when(repository.update(funko2)).thenReturn(funko2);

        var res =repository.update(funko2);

        assertAll(
                ()->assertEquals(res.getId(),funko2.getId()),
                ()-> assertEquals(res.getUuid(),funko2.getUuid()),
                ()-> assertEquals(res.getName(),funko2.getName()),
                ()-> assertEquals(res.getModelo(),funko2.getModelo()),
                ()-> assertEquals(res.getPrecio(),funko2.getPrecio()),
                ()-> assertEquals(res.getFecha_lanzamiento(),funko2.getFecha_lanzamiento())
        );
    }

         @Test
    void updateNotFunkoTest() throws SQLException {

        when(repository.update(funko1)).thenThrow(new FunkoNotFoundException("Funko no encontrado en la BD"));

        try {
            var res = repository.update(funko1);
        }catch (FunkoNotFoundException e){
            assertEquals("Funko no encontrado en la BD", e.getMessage());
        }

        verify(repository, times(1)).update(funko1);
    }
    @Test
    void findByIdTest() throws SQLException {

        when(repository.findById(1)).thenReturn(Optional.of(funko1));
        var funkID = repository.findById(1);

        assertAll(
                ()-> assertTrue(funkID.isPresent()),
                ()-> assertEquals(funko1.getName(),funkID.get().getName()),
                ()-> assertEquals(funko1,funkID.get())
        );
    }

    @Test
    void notFindByIdTest() throws SQLException {

    when(repository.findById(20)).thenReturn(Optional.empty());
        var funkID = repository.findById(20);

        assertAll(
                ()-> assertFalse(funkID.isPresent())
        );
    }
    @Test
    void findAllTest() throws SQLException {
        var list = List.of(funko1,funko2);
        when(repository.findAll()).thenReturn(list);
        var listFunkos = repository.findAll();

        assertAll(
                ()-> assertFalse(listFunkos.isEmpty()),
                ()-> assertTrue((listFunkos.size() == 2)),
                ()-> assertEquals(listFunkos.get(0),funko1),
                ()-> assertEquals(listFunkos.get(1),funko2)
        );
    }

    @Test
    void deleteByIdTest() throws SQLException {
        when(repository.deleteById(1)).thenReturn(true);
        var funkDel=repository.deleteById(1);

        assertAll(
                ()-> assertTrue(funkDel)
        );
    }

    @Test
    void deleteByIdTestError() throws SQLException{

        when(repository.deleteById(20)).thenThrow(new FunkoNotFoundException("Funko con ID 20 no encontrado en la BD"));
        try{
            var res = repository.deleteById(20);
        }catch (FunkoNotFoundException e){
            assertEquals("Funko con ID 20 no encontrado en la BD",e.getMessage());
        }

        verify(repository, times(1)).deleteById(20);
    }

    @Test
    void deleteAllTest() throws SQLException {
        when(repository.findAll()).thenReturn(new ArrayList<>());
        repository.deleteAll();
        var list = repository.findAll();
        assertAll(
                ()->assertTrue(list.isEmpty())
        );
    }


    @Test
    void findByNameTest() throws SQLException {
        var list = List.of(funko1);
        when(repository.findByName(funko1.getName())).thenReturn(list);

        var funkName = repository.findByName(funko1.getName());

        assertAll(
                ()-> assertFalse(funkName.isEmpty()),
                ()-> assertTrue(funkName.size() >= 1),
                ()-> assertEquals(funko1,funkName.get(0))
        );
    }

    @Test
    void backupTest() throws SQLException {
        when(repository.backup("funkosTest")).thenReturn(true);

        var back = repository.backup("funkosTest");

        assertTrue(back);
    }
}