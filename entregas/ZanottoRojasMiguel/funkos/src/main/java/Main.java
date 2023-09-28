import org.controllers.FunkoController;

import java.io.FileNotFoundException;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws FileNotFoundException, SQLException {
        FunkoController funkos = new FunkoController();
        funkos.run();
    }

}
