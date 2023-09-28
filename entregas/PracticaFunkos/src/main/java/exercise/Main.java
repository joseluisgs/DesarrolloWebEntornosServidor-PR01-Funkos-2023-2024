package exercise;

import java.io.FileNotFoundException;
import java.sql.SQLException;
        public class Main {
            public static void main(String[] args) throws FileNotFoundException, SQLException, FunkoException {
                System.out.println("Hola Base de datos con Java y JDBC");

                // Ahora te lo voy a repetir con un repositorio, para que veas que todo queda encapsulado y más limpio
                FunkosService FunkosService = FunkosServiceImpl.getInstance(FunkosRepositoryImpl.getInstance(DatabaseManager.getInstance()));

                // Obtenemos todos los Funkos
                System.out.println("Todos los Funkos");
                FunkosService.findAll().forEach(System.out::println);

                // Obtenemos un Funko por id
                System.out.println("Funko con id 1");
                System.out.println(FunkosService.findById(1L));

                // Obtenemos los Funkos por Name
                System.out.println("Funkos con Name Juan");
                FunkosService.findAllByName("Juan").forEach(System.out::println);

                // Insertamos 3 Funkos
                System.out.println("Insertamos 3 Funkos");
                var Funko = Funko.builder().id(0L).Name("Carolina").calificacion(7.0).build();
                System.out.println(FunkosService.save(Funko));
                Funko = Funko.builder().id(0L).Name("Sara").calificacion(6.75).build();
                System.out.println(FunkosService.save(Funko));
                Funko = Funko.builder().id(0L).Name("Enrique").calificacion(8.0).build();
                System.out.println(FunkosService.save(Funko));

                // Obtenemos todos los Funkos
                System.out.println("Todos los Funkos");
                FunkosService.findAll().forEach(System.out::println);

                // Actualizamos el Funko con id 1
                System.out.println("Actualizamos el Funko con id 1");
                // Lo buscamos
                Funko = FunkosService.findById(1L).orElseThrow();
                // Lo modificamos
                Funko.setName("Funko Updated");
                Funko.setCalificacion(9.0);
                System.out.println(FunkosService.update(Funko));

                // obtenemos todos los Funkos
                System.out.println("Todos los Funkos");
                FunkosService.findAll().forEach(System.out::println);

                // Borramos el Funko con id 1
                System.out.println("Borramos el Funko con id 1");
                Funko = FunkosService.findById(1L).orElseThrow();
                var deleted = FunkosService.deleteById(Funko.getId());
                if (deleted) {
                    System.out.println("Funko borrado: " + Funko);
                } else {
                    System.out.println("Funko no borrado porque no existe");
                }

                // obtenemos todos los Funkos
                System.out.println("Todos los Funkos");
                FunkosService.findAll().forEach(System.out::println);

                // Actualizamos algo que no existe
                System.out.println("Actualizamos el Funko con id -99");
                // Lo buscamos
                try {
                    Funko = Funko.builder().id(-99L).Name("Funko Updated").calificacion(9.0).build();
                    System.out.println(FunkosService.update(Funko));
                } catch (FunkoNoEncotradoException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
