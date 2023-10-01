import dev.controllers.FunkoController;
import dev.models.Funko;
import dev.models.Modelo;
import dev.services.FunkoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;


import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FunkoServiceTest {

    @Spy
    private FunkoService funkoService = spy(new FunkoService());

    @InjectMocks
    private FunkoController funkoController;

    List<Funko> funkos;

    @BeforeEach
    void setUp() throws SQLException, IOException {

        MockitoAnnotations.initMocks(this);

        funkos = List.of(
                new Funko(UUID.fromString("1d6c6f58-7c6b-434b-82ab-01d2d6e4434b"), "Funko1", Modelo.ANIME, 10.0, LocalDate.of(2021, 1, 1)),
                new Funko(UUID.fromString("2d6c6f58-7c6b-434b-82ab-01d2d6e4434b"), "Funko2", Modelo.ANIME, 20.0, LocalDate.of(2023, 12, 1)),
                new Funko(UUID.fromString("3d6c6f58-7c6b-434b-82ab-01d2d6e4434b"), "Fuwwwo3", Modelo.MARVEL, 30.0, LocalDate.of(2023, 2, 10)),
                new Funko(UUID.fromString("4d6c6f58-7c6b-434b-82ab-01d2d6e4434b"), "eeeko4", Modelo.OTROS, 40.0, LocalDate.of(2002, 6, 2)),
                new Funko(UUID.fromString("5d6c6f58-7c6b-434b-82ab-01d2d6e4434b"), "Funko5", Modelo.DISNEY, 50.0, LocalDate.of(2016, 4, 5))
        );

    }


    @Test
    public void testFunkoMasCaroWork() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(funkos);

            Optional<Funko> funko = funkoController.funkoMasCaro();

            assertEquals(funkos.get(4), funko.get());
    }

    @Test
    public void testFunkoMasCaroFail() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(List.of());

            Optional<Funko> funko = funkoController.funkoMasCaro();

            assertEquals(Optional.empty(), funko);

            verify(funkoService, times(1)).getFunkos();
    }

    @Test
    public void testMediaPrecioFunkosWork() throws SQLException, IOException {

        when(funkoService.getFunkos()).thenReturn(funkos);

        double media = funkoController.mediaPrecioFunkos();

        assertEquals(30.0, media);

        verify(funkoService, times(1)).getFunkos();
    }

    @Test
    public void testMediaPrecioFunkosFail() throws SQLException, IOException {

        when(funkoService.getFunkos()).thenReturn(List.of());

        double media = funkoController.mediaPrecioFunkos();

        assertEquals(0.0, media);

        verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkoPorModelosWork() throws SQLException, IOException {

        when(funkoService.getFunkos()).thenReturn(funkos);

        Map<Modelo, List<Funko>> funkosPorModelo = funkoController.funkosPorModelo();

        assertAll(
                () -> assertEquals(1, funkosPorModelo.get(Modelo.OTROS).size()),
                () -> assertEquals(1, funkosPorModelo.get(Modelo.DISNEY).size()),
                () -> assertEquals(1, funkosPorModelo.get(Modelo.MARVEL).size()),
                () -> assertEquals(2, funkosPorModelo.get(Modelo.ANIME).size())
        );

        verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkoPorModelosFail() throws SQLException, IOException {

        when(funkoService.getFunkos()).thenReturn(List.of());

        Map<Modelo, List<Funko>> funkosPorModelo = funkoController.funkosPorModelo();

        assertEquals(0, funkosPorModelo.size());

        verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testNumFunkosPorModelosWork() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(funkos);

            Map<Modelo, Integer> numFunkosPorModelo = funkoController.numFunkosPorModelo();

            assertAll(
                    () -> assertEquals(1, numFunkosPorModelo.get(Modelo.OTROS)),
                    () -> assertEquals(1, numFunkosPorModelo.get(Modelo.DISNEY)),
                    () -> assertEquals(1, numFunkosPorModelo.get(Modelo.MARVEL)),
                    () -> assertEquals(2, numFunkosPorModelo.get(Modelo.ANIME))
            );

            verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testNumFunkosPorModelosFail() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(List.of());

            Map<Modelo, Integer> numFunkosPorModelo = funkoController.numFunkosPorModelo();

            assertEquals(0, numFunkosPorModelo.size());

            verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkosLanzadosEnAnoEspecificoWork() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(funkos);

            List<Funko> funkosLanzadosEnAnoEspecifico = funkoController.funkosLanzadosEnAnoEspecifico(2023);

            assertAll(
                    ()->assertEquals(2, funkosLanzadosEnAnoEspecifico.size()),
                    ()->assertEquals(funkos.get(1), funkosLanzadosEnAnoEspecifico.get(0)),
                    ()->assertEquals(funkos.get(2), funkosLanzadosEnAnoEspecifico.get(1))
            );

            verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkosLanzadosEnAnoEspecificoFail() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(List.of());

            List<Funko> funkosLanzadosEnAnoEspecifico = funkoController.funkosLanzadosEnAnoEspecifico(2023);

            assertEquals(0, funkosLanzadosEnAnoEspecifico.size());

            verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkosContienenPalabraWork() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(funkos);

            List<Funko> funkosContienenPalabra = funkoController.funkosContienenPalabra("Funko");

            assertAll(
                    ()->assertEquals(3, funkosContienenPalabra.size()),
                    ()->assertEquals(funkos.get(0), funkosContienenPalabra.get(0)),
                    ()->assertEquals(funkos.get(1), funkosContienenPalabra.get(1)),
                    ()->assertEquals(funkos.get(4), funkosContienenPalabra.get(2))
            );

            verify(funkoService, times(1)).getFunkos();

    }

    @Test
    public void testFunkosContienenPalabraFail() throws SQLException, IOException {

            when(funkoService.getFunkos()).thenReturn(List.of());

            List<Funko> funkosContienenPalabra = funkoController.funkosContienenPalabra("Funko");

            assertEquals(0, funkosContienenPalabra.size());

            verify(funkoService, times(1)).getFunkos();

    }


}
