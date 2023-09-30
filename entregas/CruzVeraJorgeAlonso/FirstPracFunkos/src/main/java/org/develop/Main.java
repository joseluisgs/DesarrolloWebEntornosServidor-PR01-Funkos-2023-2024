package org.develop;

import org.develop.excepciones.FunkoException;
import org.develop.model.Funko;
import org.develop.repository.FunkoRepositoryImpl;
import org.develop.services.database.DatabaseManager;
import org.develop.services.file.ReadCSVFunkos;
import org.develop.services.funko.FunkoServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    private final Logger logger = LoggerFactory.getLogger(Main.class);
    public void operacionesStreamBD(FunkoServiceImpl fkImp,List<Funko> funkos) throws SQLException {
        logger.debug("Busqueda del Funko mas caro");
        var funkMoreExpen = funkos.stream()
                .max(Comparator.comparingDouble(Funko::getPrecio));
        System.out.println(funkMoreExpen);

        logger.debug("Media de precio de Funkos");
        var funkPricAverage = funkos.stream()
                .mapToDouble(Funko::getPrecio)
                .average();
        System.out.println("Media de precios : " + funkPricAverage);

        logger.debug("Funkos Agrupados por Modelo");
        var funkType= funkos.stream()
                .map(Funko::getModelo)
                .distinct()
                .collect(Collectors.toMap(fk->fk,
                        fk-> funkos.stream()
                        .filter(fkT -> fkT.getModelo().equals(fk))
                        .toList()));
        funkType.forEach((a,b) -> System.out.println(a + " : " + b));

        logger.debug("Numero de Funkos por Modelo");
        var funkCountType = funkos.stream()
                .map(Funko::getModelo)
                .collect(Collectors.groupingBy(fk->fk,Collectors.counting()));
        funkCountType.forEach((a,b) -> System.out.println(a + " : " + b));

        logger.debug("Funkos Lanzados en el 2023");
        var funkLaunchDate = funkos.stream()
                .filter(fk -> fk.getFecha_lanzamiento().toString().contains("2023"))
                .toList();
        funkLaunchDate.forEach(System.out::println);

        logger.debug("Funkos de Stitch");
        var count = funkos.stream()
        .filter(fk -> fk.getName().contains("Stitch"))
        .count();

        var stitchFunkos = funkos.stream()
                .filter(fk -> fk.getName().contains("Stitch"))
                .collect(Collectors.groupingBy(
                        fk -> count,  // Utiliza la cantidad como clave
                        Collectors.toList()
                ));

        System.out.println("Funkos de Stitch : ");
        stitchFunkos.forEach((a,b) -> System.out.println(a + " : " + b));
    }

    public static void main(String[] args) throws SQLException, FunkoException {
        ReadCSVFunkos rs = new ReadCSVFunkos();
        Main main = new Main();
        DatabaseManager db = DatabaseManager.getInstance();
        FunkoRepositoryImpl fkRepImp = FunkoRepositoryImpl.getInstance(db);
        FunkoServiceImpl fkImp = FunkoServiceImpl.getInstance(fkRepImp);



//        //Insercion en Base de Datos del fichero
//            for (Funko funko : rs.readFileFunko()) {
//                fkImp.save(funko);
//            }
//
//            var atr = fkImp.backup("funkos");
//            List<Funko> funkos = fkImp.findAll();
//
//            main.operacionesStreamBD(fkImp,funkos);

//
//        //Obtener Funko por ID
//        var funk = fkImp.findById(4).orElseGet(Funko::new);
//        System.out.println(funk);
//        //Obtener Funko por ID || ERROR
//        var funkErr = fkImp.findById(-1).orElseGet(Funko::new);
//        System.out.println(funk);
//
//        //Update de Funko en Base de Datos
//        funk.setName("Pam Besley The Office");
//        funk.setModelo(Modelo.OTROS);
//        funk.setPrecio(13.5);
//
//        var funk1= fkImp.update(funk);
//        System.out.println(funk1);
//        //Update de Funko en Base de Datos || ERROR
//        var funkErr1 = new Funko();
//        funkErr1.setName("Pam Besley The Office");
//        funkErr1.setModelo(Modelo.OTROS);
//        funkErr1.setPrecio(13.5);
//
//        var funk1Err= fkImp.update(funkErr1);
//        System.out.println(funk1Err);
//
//        //Obtener Funkos por Patron Nombre
//        var funk2 = fkImp.findByName("Pa");
//        funk2.forEach(System.out::println);
//        //Obtener Funkos por Patron Nombre || ERROR
//        var funkErr2 = fkImp.findByName("*132");
//        funkErr2.forEach(System.out::println);
//
//        //Eliminar Funko por ID
//        var funkDel = fkImp.deleteById(4);
//        //Eliminar Funko por ID
//        var funkDelErr = fkImp.deleteById(10000);
//
//        //Obtener todos los Funkos de la base de datos
//        fkImp.findAll().forEach(System.out::println);
//
//        //Backup de los Funkos en JSON
//        var bk = fkImp.backup("funkos");
//        System.out.println(bk);
//
//        //Eliminar todos los Funkos de la base de datos
//        fkImp.deleteAll();
//
//        //Comprobacion Delete
//        fkImp.findAll().forEach(System.out::println);

    }
}