import controllers.FunkoController;
import services.FunkoService;

public class Main {
    public static void main(String[] args) {
        FunkoController funkoController = new FunkoController(FunkoService.getInstance());
        funkoController.importCsv();
        System.out.println("Funko más caro: " + funkoController.getMostExpensiveFunko());
        System.out.println("Precio medio: " + funkoController.getAvgPrice());
        System.out.println("Funkos agrupados por modelo: " + funkoController.getGroupedByModels());
        System.out.println("Número de funkos por modelo: " + funkoController.getCountByModels());
        System.out.println("Funkos lanzados en 2023: " + funkoController.getLaunchedIn2023());
        System.out.println("Funkos de Stitch " + funkoController.getStitchCountAndList());
        System.out.println("Exportando a JSON");
        funkoController.exportToJson();
        System.out.println("Exportado a JSON");

        System.out.println("Buscando por nombre: Naruto Shippuden " + funkoController.findByNombre("Naruto Shippuden"));
    }
}
