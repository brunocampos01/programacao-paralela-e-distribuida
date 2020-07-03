package servidor;

public class MainApp {

    public static void main(String[] args) {
        Gerenciador gerenciador = new Gerenciador();
        gerenciador.criar(); //cria um processo "coordenador"
        gerenciador.inicializar();
        gerenciador.encerrar();
    }
}
