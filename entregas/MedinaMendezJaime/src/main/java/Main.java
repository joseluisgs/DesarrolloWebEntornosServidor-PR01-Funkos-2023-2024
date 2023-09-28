import Models.FunkoBD;
import Service.DB;
import Service.FunkoManager;
import controllers.FunkoController;

public class Main {
    public static void main(String[] args) {
        DB db = DB.getInstance();

        FunkoManager funkoManager = FunkoManager.getInstance(db.getConnection());
        FunkoController funkoController = FunkoController.getInstance();

        funkoController.loadCsv();

        for (FunkoBD funko : funkoController.getFunkos()) {
            funkoManager.save(funko);
        }


        /*funkoController.exportJson();*/
        System.out.println(funkoController.getStitchList());


    }
}
