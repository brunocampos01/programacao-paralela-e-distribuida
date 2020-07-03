package tokenring;

public class Servidor implements Runnable {

    Socket socket = null;
    static ServerSocket ss;

    public Servidor(Socket newSocket) {
        this.socket = newSocket;
    }

    public static void main(String args[]) throws IOException {

        ss = new ServerSocket(7000);

        System.out.println("Servidor Iniciado");

        while (true) {
            Socket s = ss.accept();
            Servidor es = new Servidor(s); //instancia o Servidor passando o respectivo socket
            Thread t = new Thread(es); //instancia a Thread passando o servidor
            t.start();

        }


    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                System.out.println(in.readLine());
            }
        } catch (Exception e) {
        }
    }
}
