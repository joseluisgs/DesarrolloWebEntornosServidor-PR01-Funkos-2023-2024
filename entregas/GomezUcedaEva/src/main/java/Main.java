import controllers.FunkosControllers;

public class Main {
    public static void main(String[] args) {
        FunkosControllers funkosControllers = FunkosControllers.getInstance();
        funkosControllers.loadFunkos();
        System.out.println(funkosControllers.funkoMasCaro());
        System.out.println(funkosControllers.mediaPrecios());
        //System.out.println(funkosControllers.agrupadosPorModelo());

    }
}