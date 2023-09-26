import controllers.FunkoController;
import services.FunkoService;

public class Main {
    public static void main(String[] args) {
        FunkoController funkoController = new FunkoController(FunkoService.getInstance());
        funkoController.importCsv();
    }
}
